package com.example.uts_databuku

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_databuku.data.session.SessionManager003
import com.example.uts_databuku.ui.auth.LoginActivity003
import com.example.uts_databuku.ui.dashboard.DashboardActivity003

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState_003: Bundle?) {
        super.onCreate(savedInstanceState_003)

        val sessionManager_003 = SessionManager003(this)
        if (sessionManager_003.isLoggedIn003()) {
            val intent_003 = Intent(this, DashboardActivity003::class.java)
            startActivity(intent_003)
        } else {
            val intent_003 = Intent(this, LoginActivity003::class.java)
            startActivity(intent_003)
        }
        finish()
    }
}