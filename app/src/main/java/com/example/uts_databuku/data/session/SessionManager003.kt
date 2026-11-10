package com.example.uts_databuku.data.session

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SessionManager003(context_003: Context) {

    private val pref_003: SharedPreferences = try {
        val masterKey_003 = MasterKey.Builder(context_003.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context_003.applicationContext,
            PREF_NAME_003,
            masterKey_003,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } catch (e_003: Exception) {
        context_003.getSharedPreferences(PREF_NAME_003, Context.MODE_PRIVATE)
    }

    fun createSession003(userId_003: Int, username_003: String) {
        val editor_003 = pref_003.edit()
        editor_003.putInt(KEY_USER_ID_003, userId_003)
        editor_003.putString(KEY_USERNAME_003, username_003)
        editor_003.putBoolean(KEY_IS_LOGGED_IN_003, true)
        editor_003.apply()
    }

    fun getUserId003(): Int {
        return pref_003.getInt(KEY_USER_ID_003, -1)
    }

    fun getUsername003(): String {
        return pref_003.getString(KEY_USERNAME_003, "") ?: ""
    }

    fun isLoggedIn003(): Boolean {
        return pref_003.getBoolean(KEY_IS_LOGGED_IN_003, false) && getUserId003() != -1
    }

    fun clearSession003() {
        val editor_003 = pref_003.edit()
        editor_003.clear()
        editor_003.apply()
    }

    companion object {
        private const val PREF_NAME_003 = "user_session_003_pref"
        private const val KEY_USER_ID_003 = "user_id_003"
        private const val KEY_USERNAME_003 = "username_003"
        private const val KEY_IS_LOGGED_IN_003 = "is_logged_in_003"
    }
}