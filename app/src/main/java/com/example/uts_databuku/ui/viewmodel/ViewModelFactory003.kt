package com.example.uts_databuku.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.uts_databuku.data.database.AppDatabase003
import com.example.uts_databuku.data.repository.AuthRepository003
import com.example.uts_databuku.data.repository.BookRepository003

class ViewModelFactory003 private constructor(
    private val authRepository_003: AuthRepository003,
    private val bookRepository_003: BookRepository003
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel003::class.java) -> {
                AuthViewModel003(authRepository_003) as T
            }
            modelClass.isAssignableFrom(BookViewModel003::class.java) -> {
                BookViewModel003(bookRepository_003) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE_003: ViewModelFactory003? = null

        fun getInstance003(context_003: Context): ViewModelFactory003 {
            return INSTANCE_003 ?: synchronized(this) {
                val db_003 = AppDatabase003.getInstance003(context_003)
                val authRepo_003 = AuthRepository003(db_003.userDao003())
                val bookRepo_003 = BookRepository003(db_003.bookDao003())
                val instance_003 = ViewModelFactory003(authRepo_003, bookRepo_003)
                INSTANCE_003 = instance_003
                instance_003
            }
        }
    }
}