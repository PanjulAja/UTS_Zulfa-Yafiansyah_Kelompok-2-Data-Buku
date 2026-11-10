package com.example.uts_databuku.data.repository

import com.example.uts_databuku.data.dao.BookDao003
import com.example.uts_databuku.data.entity.BookEntity003
import com.example.uts_databuku.utils.SecurityUtils003
import kotlinx.coroutines.flow.Flow

class BookRepository003(private val bookDao_003: BookDao003) {

    fun getBooksForUser003(userId_003: Int): Flow<List<BookEntity003>> {
        return bookDao_003.getBooksByUserId003(userId_003)
    }

    fun searchBooksForUser003(userId_003: Int, query_003: String): Flow<List<BookEntity003>> {
        val cleanQuery_003 = SecurityUtils003.sanitizeInput003(query_003)
        return bookDao_003.searchBooks003(userId_003, cleanQuery_003)
    }

    fun getTotalHargaForUser003(userId_003: Int): Flow<Double> {
        return bookDao_003.getTotalHargaByUserId003(userId_003)
    }

    suspend fun getBookById003(bookId_003: Int, userId_003: Int): BookEntity003? {
        return bookDao_003.getBookById003(bookId_003, userId_003)
    }

    suspend fun insertBook003(
        userId_003: Int,
        judul_003: String,
        pengarang_003: String,
        tahunStr_003: String,
        hargaStr_003: String
    ): Result<Long> {
        val cleanJudul_003 = SecurityUtils003.sanitizeInput003(judul_003)
        val cleanPengarang_003 = SecurityUtils003.sanitizeInput003(pengarang_003)

        if (cleanJudul_003.isEmpty()) {
            return Result.failure(Exception("Judul buku tidak boleh kosong!"))
        }

        if (cleanPengarang_003.isEmpty()) {
            return Result.failure(Exception("Nama pengarang tidak boleh kosong!"))
        }

        val tahun_003 = tahunStr_003.trim().toIntOrNull()
        if (tahun_003 == null || tahun_003 <= 0) {
            return Result.failure(Exception("Tahun terbit harus berupa angka tahun yang valid!"))
        }

        val harga_003 = hargaStr_003.trim().toDoubleOrNull() ?: 0.0
        if (harga_003 < 0) {
            return Result.failure(Exception("Harga buku tidak boleh bernilai negatif!"))
        }

        val newBook_003 = BookEntity003(
            userId_003 = userId_003,
            judul_003 = cleanJudul_003,
            pengarang_003 = cleanPengarang_003,
            tahun_003 = tahun_003,
            harga_003 = harga_003
        )

        val id_003 = bookDao_003.insertBook003(newBook_003)
        return Result.success(id_003)
    }

    suspend fun updateBook003(
        bookId_003: Int,
        userId_003: Int,
        judul_003: String,
        pengarang_003: String,
        tahunStr_003: String,
        hargaStr_003: String
    ): Result<Int> {
        val cleanJudul_003 = SecurityUtils003.sanitizeInput003(judul_003)
        val cleanPengarang_003 = SecurityUtils003.sanitizeInput003(pengarang_003)

        if (cleanJudul_003.isEmpty()) {
            return Result.failure(Exception("Judul buku tidak boleh kosong!"))
        }

        if (cleanPengarang_003.isEmpty()) {
            return Result.failure(Exception("Nama pengarang tidak boleh kosong!"))
        }

        val tahun_003 = tahunStr_003.trim().toIntOrNull()
        if (tahun_003 == null || tahun_003 <= 0) {
            return Result.failure(Exception("Tahun terbit harus berupa angka tahun yang valid!"))
        }

        val harga_003 = hargaStr_003.trim().toDoubleOrNull() ?: 0.0
        if (harga_003 < 0) {
            return Result.failure(Exception("Harga buku tidak boleh bernilai negatif!"))
        }

        val existingBook_003 = bookDao_003.getBookById003(bookId_003, userId_003)
            ?: return Result.failure(Exception("Buku tidak ditemukan atau Anda tidak memiliki akses."))

        val updatedBook_003 = existingBook_003.copy(
            judul_003 = cleanJudul_003,
            pengarang_003 = cleanPengarang_003,
            tahun_003 = tahun_003,
            harga_003 = harga_003
        )

        val rows_003 = bookDao_003.updateBook003(updatedBook_003)
        return Result.success(rows_003)
    }

    suspend fun deleteBook003(book_003: BookEntity003): Result<Int> {
        val rows_003 = bookDao_003.deleteBook003(book_003)
        return Result.success(rows_003)
    }
}