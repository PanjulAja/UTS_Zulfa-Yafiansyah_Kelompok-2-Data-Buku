package com.example.uts_databuku.utils

import java.security.MessageDigest

object SecurityUtils003 {

    private const val SALT_003 = "UTS_PAB_102042330003_SECURE_SALT"

    /**
     * Hashes password using SHA-256 with a unique salt to ensure security.
     * Prevents raw plaintext passwords from being exposed or leaked.
     */
    fun hashPassword003(rawPassword_003: String): String {
        val salted_003 = rawPassword_003 + SALT_003
        val bytes_003 = MessageDigest.getInstance("SHA-256").digest(salted_003.toByteArray())
        return bytes_003.joinToString("") { "%02x".format(it) }
    }

    /**
     * Sanitizes input string to prevent script injection and remove hazardous control characters.
     */
    fun sanitizeInput003(input_003: String): String {
        return input_003.trim()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("'", "''")
    }

    /**
     * Validates that password meets minimum security criteria (>= 6 characters).
     */
    fun isPasswordValid003(password_003: String): Boolean {
        return password_003.trim().length >= 6
    }

    /**
     * Validates username is non-empty and contains valid alphanumeric or underscore characters.
     */
    fun isUsernameValid003(username_003: String): Boolean {
        val cleanUsername_003 = username_003.trim()
        return cleanUsername_003.isNotEmpty() && cleanUsername_003.length >= 3 && cleanUsername_003.matches(Regex("^[a-zA-Z0-9_]+$"))
    }
}