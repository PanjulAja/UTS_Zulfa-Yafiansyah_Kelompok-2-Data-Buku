package com.example.uts_databuku.data.repository

import com.example.uts_databuku.data.dao.UserDao003
import com.example.uts_databuku.data.entity.UserEntity003
import com.example.uts_databuku.utils.SecurityUtils003

class AuthRepository003(private val userDao_003: UserDao003) {

    suspend fun registerUser003(username_003: String, rawPassword_003: String): Result<UserEntity003> {
        val cleanUsername_003 = SecurityUtils003.sanitizeInput003(username_003)

        if (!SecurityUtils003.isUsernameValid003(cleanUsername_003)) {
            return Result.failure(Exception("Username tidak valid! Hanya boleh huruf, angka, dan underscore (min. 3 karakter)."))
        }

        if (!SecurityUtils003.isPasswordValid003(rawPassword_003)) {
            return Result.failure(Exception("Password harus minimal 6 karakter!"))
        }

        val existingUser_003 = userDao_003.getUserByUsername003(cleanUsername_003)
        if (existingUser_003 != null) {
            return Result.failure(Exception("Username sudah terdaftar! Gunakan username lain."))
        }

        val hashedPassword_003 = SecurityUtils003.hashPassword003(rawPassword_003)
        val newUser_003 = UserEntity003(
            username_003 = cleanUsername_003,
            password_003 = hashedPassword_003
        )

        val insertedId_003 = userDao_003.insertUser003(newUser_003)
        return if (insertedId_003 > 0) {
            Result.success(newUser_003.copy(id_003 = insertedId_003.toInt()))
        } else {
            Result.failure(Exception("Gagal mendaftarkan akun baru."))
        }
    }

    suspend fun loginUser003(username_003: String, rawPassword_003: String): Result<UserEntity003> {
        val cleanUsername_003 = SecurityUtils003.sanitizeInput003(username_003)
        val user_003 = userDao_003.getUserByUsername003(cleanUsername_003)
            ?: return Result.failure(Exception("Username tidak ditemukan!"))

        val hashedInputPassword_003 = SecurityUtils003.hashPassword003(rawPassword_003)
        return if (user_003.password_003 == hashedInputPassword_003) {
            Result.success(user_003)
        } else {
            Result.failure(Exception("Password salah! Silakan periksa kembali."))
        }
    }
}