package com.example.uts_databuku.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uts_databuku.data.entity.UserEntity003

@Dao
interface UserDao003 {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser003(user_003: UserEntity003): Long

    @Query("SELECT * FROM users_003 WHERE username_003 = :username_003 LIMIT 1")
    suspend fun getUserByUsername003(username_003: String): UserEntity003?

    @Query("SELECT * FROM users_003 WHERE id_003 = :id_003 LIMIT 1")
    suspend fun getUserById003(id_003: Int): UserEntity003?
}