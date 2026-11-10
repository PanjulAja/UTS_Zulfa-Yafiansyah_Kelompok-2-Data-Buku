package com.example.uts_databuku.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uts_databuku.data.dao.BookDao003
import com.example.uts_databuku.data.dao.UserDao003
import com.example.uts_databuku.data.entity.BookEntity003
import com.example.uts_databuku.data.entity.UserEntity003

@Database(
    entities = [UserEntity003::class, BookEntity003::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase003 : RoomDatabase() {

    abstract fun userDao003(): UserDao003
    abstract fun bookDao003(): BookDao003

    companion object {
        @Volatile
        private var INSTANCE_003: AppDatabase003? = null

        fun getInstance003(context_003: Context): AppDatabase003 {
            return INSTANCE_003 ?: synchronized(this) {
                val instance_003 = Room.databaseBuilder(
                    context_003.applicationContext,
                    AppDatabase003::class.java,
                    "databuku_db_003"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE_003 = instance_003
                instance_003
            }
        }
    }
}