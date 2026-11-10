package com.example.uts_databuku.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.uts_databuku.data.entity.BookEntity003
import com.example.uts_databuku.data.repository.BookRepository003
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BookViewModel003(private val bookRepository_003: BookRepository003) : ViewModel() {

    private val _currentUserId_003 = MutableStateFlow(-1)
    private val _searchQuery_003 = MutableStateFlow("")

    val booksList_003: LiveData<List<BookEntity003>> = _searchQuery_003.flatMapLatest { query_003 ->
        val userId_003 = _currentUserId_003.value
        if (query_003.isBlank()) {
            bookRepository_003.getBooksForUser003(userId_003)
        } else {
            bookRepository_003.searchBooksForUser003(userId_003, query_003)
        }
    }.asLiveData()

    val totalHarga_003: LiveData<Double> = _currentUserId_003.flatMapLatest { userId_003 ->
        bookRepository_003.getTotalHargaForUser003(userId_003)
    }.asLiveData()

    private val _operationResult_003 = MutableLiveData<Result<String>>()
    val operationResult_003: LiveData<Result<String>> = _operationResult_003

    private val _selectedBook_003 = MutableLiveData<BookEntity003?>()
    val selectedBook_003: LiveData<BookEntity003?> = _selectedBook_003

    fun initUser003(userId_003: Int) {
        _currentUserId_003.value = userId_003
    }

    fun setSearchQuery003(query_003: String) {
        _searchQuery_003.value = query_003
    }

    fun loadBookById003(bookId_003: Int, userId_003: Int) {
        viewModelScope.launch {
            val book_003 = bookRepository_003.getBookById003(bookId_003, userId_003)
            _selectedBook_003.value = book_003
        }
    }

    fun saveBook003(
        bookId_003: Int,
        userId_003: Int,
        judul_003: String,
        pengarang_003: String,
        tahun_003: String,
        harga_003: String
    ) {
        viewModelScope.launch {
            if (bookId_003 == -1) {
                // Mode Insert
                val res_003 = bookRepository_003.insertBook003(
                    userId_003, judul_003, pengarang_003, tahun_003, harga_003
                )
                if (res_003.isSuccess) {
                    _operationResult_003.value = Result.success("Buku berhasil ditambahkan!")
                } else {
                    _operationResult_003.value = Result.failure(res_003.exceptionOrNull() ?: Exception("Gagal menyimpan buku."))
                }
            } else {
                // Mode Update
                val res_003 = bookRepository_003.updateBook003(
                    bookId_003, userId_003, judul_003, pengarang_003, tahun_003, harga_003
                )
                if (res_003.isSuccess) {
                    _operationResult_003.value = Result.success("Buku berhasil diperbarui!")
                } else {
                    _operationResult_003.value = Result.failure(res_003.exceptionOrNull() ?: Exception("Gagal memperbarui buku."))
                }
            }
        }
    }

    fun deleteBook003(book_003: BookEntity003) {
        viewModelScope.launch {
            val res_003 = bookRepository_003.deleteBook003(book_003)
            if (res_003.isSuccess) {
                _operationResult_003.value = Result.success("Buku berhasil dihapus!")
            } else {
                _operationResult_003.value = Result.failure(res_003.exceptionOrNull() ?: Exception("Gagal menghapus buku."))
            }
        }
    }
}