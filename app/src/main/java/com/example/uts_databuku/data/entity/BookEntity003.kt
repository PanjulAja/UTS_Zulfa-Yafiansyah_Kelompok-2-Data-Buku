package com.example.uts_databuku.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "books_003",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity003::class,
            parentColumns = ["id_003"],
            childColumns = ["user_id_003"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id_003"])]
)
data class BookEntity003(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_003")
    val id_003: Int = 0,

    @ColumnInfo(name = "user_id_003")
    val userId_003: Int,

    @ColumnInfo(name = "judul_003")
    val judul_003: String,

    @ColumnInfo(name = "pengarang_003")
    val pengarang_003: String,

    @ColumnInfo(name = "tahun_003")
    val tahun_003: Int,

    @ColumnInfo(name = "harga_003")
    val harga_003: Double = 0.0
)