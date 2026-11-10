package com.example.uts_databuku.ui.book

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_databuku.R
import com.example.uts_databuku.data.session.SessionManager003
import com.example.uts_databuku.databinding.ActivityBookForm003Binding
import com.example.uts_databuku.ui.viewmodel.BookViewModel003
import com.example.uts_databuku.ui.viewmodel.ViewModelFactory003

class BookFormActivity003 : AppCompatActivity() {

    private lateinit var binding_003: ActivityBookForm003Binding
    private lateinit var sessionManager_003: SessionManager003

    private var bookId_003: Int = -1

    private val bookViewModel_003: BookViewModel003 by viewModels {
        ViewModelFactory003.getInstance003(this)
    }

    override fun onCreate(savedInstanceState_003: Bundle?) {
        super.onCreate(savedInstanceState_003)
        binding_003 = ActivityBookForm003Binding.inflate(layoutInflater)
        setContentView(binding_003.root)

        sessionManager_003 = SessionManager003(this)
        bookId_003 = intent.getIntExtra(EXTRA_BOOK_ID_003, -1)

        setupFormUI003()
        setupListeners003()
        observeViewModel003()
    }

    private fun setupFormUI003() {
        if (bookId_003 != -1) {
            binding_003.tvFormTitle003.text = getString(R.string.title_edit_book_003)
            binding_003.btnFormSave003.text = getString(R.string.btn_update_003)
            val currentUserId_003 = sessionManager_003.getUserId003()
            bookViewModel_003.loadBookById003(bookId_003, currentUserId_003)
        } else {
            binding_003.tvFormTitle003.text = getString(R.string.title_add_book_003)
            binding_003.btnFormSave003.text = getString(R.string.btn_save_003)
        }
    }

    private fun setupListeners003() {
        binding_003.btnFormSave003.setOnClickListener {
            val judul_003 = binding_003.etFormJudul003.text.toString()
            val pengarang_003 = binding_003.etFormPengarang003.text.toString()
            val tahun_003 = binding_003.etFormTahun003.text.toString()
            val harga_003 = binding_003.etFormHarga003.text.toString()

            val currentUserId_003 = sessionManager_003.getUserId003()

            bookViewModel_003.saveBook003(
                bookId_003 = bookId_003,
                userId_003 = currentUserId_003,
                judul_003 = judul_003,
                pengarang_003 = pengarang_003,
                tahun_003 = tahun_003,
                harga_003 = harga_003
            )
        }

        binding_003.btnFormCancel003.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel003() {
        bookViewModel_003.selectedBook_003.observe(this) { book_003 ->
            if (book_003 != null && bookId_003 != -1) {
                binding_003.etFormJudul003.setText(book_003.judul_003)
                binding_003.etFormPengarang003.setText(book_003.pengarang_003)
                binding_003.etFormTahun003.setText(book_003.tahun_003.toString())
                binding_003.etFormHarga003.setText(book_003.harga_003.toString())
            }
        }

        bookViewModel_003.operationResult_003.observe(this) { result_003 ->
            result_003.fold(
                onSuccess = { msg_003 ->
                    Toast.makeText(this, msg_003, Toast.LENGTH_SHORT).show()
                    finish()
                },
                onFailure = { ex_003 ->
                    Toast.makeText(this, ex_003.message ?: "Gagal menyimpan data!", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    companion object {
        const val EXTRA_BOOK_ID_003 = "extra_book_id_003"
    }
}