package com.example.uts_databuku.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.uts_databuku.data.entity.BookEntity003
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao003 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook003(book_003: BookEntity003): Long

    @Update
    suspend fun updateBook003(book_003: BookEntity003): Int

    @Delete
    suspend fun deleteBook003(book_003: BookEntity003): Int

    @Query("SELECT * FROM books_003 WHERE user_id_003 = :userId_003 ORDER BY id_003 DESC")
    fun getBooksByUserId003(userId_003: Int): Flow<List<BookEntity003>>

    @Query("SELECT * FROM books_003 WHERE id_003 = :id_003 AND user_id_003 = :userId_003 LIMIT 1")
    suspend fun getBookById003(id_003: Int, userId_003: Int): BookEntity003?

    @Query("""
        SELECT * FROM books_003 
        WHERE user_id_003 = :userId_003 
        AND (pengarang_003 LIKE '%' || :searchQuery_003 || '%' 
             OR CAST(tahun_003 AS TEXT) LIKE '%' || :searchQuery_003 || '%')
        ORDER BY id_003 DESC
    """)
    fun searchBooks003(userId_003: Int, searchQuery_003: String): Flow<List<BookEntity003>>

    @Query("SELECT COALESCE(SUM(harga_003), 0.0) FROM books_003 WHERE user_id_003 = :userId_003")
    fun getTotalHargaByUserId003(userId_003: Int): Flow<Double>
}