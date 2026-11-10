package com.example.uts_databuku.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts_databuku.data.entity.BookEntity003
import com.example.uts_databuku.data.session.SessionManager003
import com.example.uts_databuku.databinding.ActivityDashboard003Binding
import com.example.uts_databuku.ui.auth.LoginActivity003
import com.example.uts_databuku.ui.book.BookAdapter003
import com.example.uts_databuku.ui.book.BookFormActivity003
import com.example.uts_databuku.ui.viewmodel.BookViewModel003
import com.example.uts_databuku.ui.viewmodel.ViewModelFactory003
import java.text.NumberFormat
import java.util.Locale

class DashboardActivity003 : AppCompatActivity() {

    private lateinit var binding_003: ActivityDashboard003Binding
    private lateinit var sessionManager_003: SessionManager003
    private lateinit var bookAdapter_003: BookAdapter003

    private val bookViewModel_003: BookViewModel003 by viewModels {
        ViewModelFactory003.getInstance003(this)
    }

    override fun onCreate(savedInstanceState_003: Bundle?) {
        super.onCreate(savedInstanceState_003)
        binding_003 = ActivityDashboard003Binding.inflate(layoutInflater)
        setContentView(binding_003.root)

        sessionManager_003 = SessionManager003(this)

        if (!sessionManager_003.isLoggedIn003()) {
            navigateToLogin003()
            return
        }

        val currentUserId_003 = sessionManager_003.getUserId003()
        val currentUsername_003 = sessionManager_003.getUsername003()

        binding_003.tvWelcomeUser003.text = "Selamat Datang, $currentUsername_003!"

        bookViewModel_003.initUser003(currentUserId_003)

        setupRecyclerView003()
        setupListeners003()
        observeViewModel003()
    }

    private fun setupRecyclerView003() {
        bookAdapter_003 = BookAdapter003(
            onEditClick_003 = { book_003 ->
                val intent_003 = Intent(this, BookFormActivity003::class.java)
                intent_003.putExtra(BookFormActivity003.EXTRA_BOOK_ID_003, book_003.id_003)
                startActivity(intent_003)
            },
            onDeleteClick_003 = { book_003 ->
                showDeleteConfirmationDialog003(book_003)
            }
        )

        binding_003.rvBooks003.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity003)
            adapter = bookAdapter_003
        }
    }

    private fun setupListeners003() {
        binding_003.btnLogout003.setOnClickListener {
            sessionManager_003.clearSession003()
            Toast.makeText(this, "Berhasil Logout.", Toast.LENGTH_SHORT).show()
            navigateToLogin003()
        }

        binding_003.fabAddBook003.setOnClickListener {
            val intent_003 = Intent(this, BookFormActivity003::class.java)
            startActivity(intent_003)
        }

        binding_003.etSearchQuery003.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s_003: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s_003: CharSequence?, start: Int, before: Int, count: Int) {
                bookViewModel_003.setSearchQuery003(s_003?.toString() ?: "")
            }

            override fun afterTextChanged(s_003: Editable?) {}
        })
    }

    private fun observeViewModel003() {
        bookViewModel_003.booksList_003.observe(this) { books_003 ->
            bookAdapter_003.submitList(books_003)
            if (books_003.isEmpty()) {
                binding_003.tvEmptyState003.visibility = View.VISIBLE
                binding_003.rvBooks003.visibility = View.GONE
            } else {
                binding_003.tvEmptyState003.visibility = View.GONE
                binding_003.rvBooks003.visibility = View.VISIBLE
            }
        }

        bookViewModel_003.totalHarga_003.observe(this) { total_003 ->
            val formatRupiah_003 = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            binding_003.tvTotalHarga003.text = formatRupiah_003.format(total_003 ?: 0.0)
        }

        bookViewModel_003.operationResult_003.observe(this) { result_003 ->
            result_003.fold(
                onSuccess = { msg_003 ->
                    Toast.makeText(this, msg_003, Toast.LENGTH_SHORT).show()
                },
                onFailure = { ex_003 ->
                    Toast.makeText(this, ex_003.message ?: "Terjadi kesalahan!", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun showDeleteConfirmationDialog003(book_003: BookEntity003) {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Hapus Buku")
            .setMessage("Apakah Anda yakin ingin menghapus buku \"${book_003.judul_003}\"?")
            .setPositiveButton("Hapus") { dialog_003, _ ->
                bookViewModel_003.deleteBook003(book_003)
                dialog_003.dismiss()
            }
            .setNegativeButton("Batal") { dialog_003, _ ->
                dialog_003.dismiss()
            }
            .show()
    }

    private fun navigateToLogin003() {
        val intent_003 = Intent(this, LoginActivity003::class.java)
        intent_003.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent_003)
        finish()
    }
}