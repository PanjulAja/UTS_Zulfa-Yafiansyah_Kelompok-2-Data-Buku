package com.example.uts_databuku.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_003",
    indices = [Index(value = ["username_003"], unique = true)]
)
data class UserEntity003(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_003")
    val id_003: Int = 0,

    @ColumnInfo(name = "username_003")
    val username_003: String,

    @ColumnInfo(name = "password_003")
    val password_003: String
)