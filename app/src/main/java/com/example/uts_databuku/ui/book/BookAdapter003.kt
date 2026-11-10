package com.example.uts_databuku.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_databuku.data.entity.BookEntity003
import com.example.uts_databuku.databinding.ItemBook003Binding
import java.text.NumberFormat
import java.util.Locale

class BookAdapter003(
    private val onEditClick_003: (BookEntity003) -> Unit,
    private val onDeleteClick_003: (BookEntity003) -> Unit
) : ListAdapter<BookEntity003, BookAdapter003.BookViewHolder003>(DIFF_CALLBACK_003) {

    override fun onCreateViewHolder(parent_003: ViewGroup, viewType_003: Int): BookViewHolder003 {
        val binding_003 = ItemBook003Binding.inflate(
            LayoutInflater.from(parent_003.context),
            parent_003,
            false
        )
        return BookViewHolder003(binding_003)
    }

    override fun onBindViewHolder(holder_003: BookViewHolder003, position_003: Int) {
        val book_003 = getItem(position_003)
        if (book_003 != null) {
            holder_003.bind003(book_003)
        }
    }

    inner class BookViewHolder003(private val binding_003: ItemBook003Binding) :
        RecyclerView.ViewHolder(binding_003.root) {

        fun bind003(book_003: BookEntity003) {
            binding_003.tvBookJudul003.text = book_003.judul_003
            binding_003.tvBookPengarang003.text = "Pengarang: ${book_003.pengarang_003}"
            binding_003.tvBookTahun003.text = "Tahun: ${book_003.tahun_003}"

            val formatRupiah_003 = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            binding_003.tvBookHarga003.text = formatRupiah_003.format(book_003.harga_003)

            binding_003.btnEditBook003.setOnClickListener {
                onEditClick_003(book_003)
            }

            binding_003.btnDeleteBook003.setOnClickListener {
                onDeleteClick_003(book_003)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK_003 = object : DiffUtil.ItemCallback<BookEntity003>() {
            override fun areItemsTheSame(
                oldItem_003: BookEntity003,
                newItem_003: BookEntity003
            ): Boolean {
                return oldItem_003.id_003 == newItem_003.id_003
            }

            override fun areContentsTheSame(
                oldItem_003: BookEntity003,
                newItem_003: BookEntity003
            ): Boolean {
                return oldItem_003 == newItem_003
            }
        }
    }
}