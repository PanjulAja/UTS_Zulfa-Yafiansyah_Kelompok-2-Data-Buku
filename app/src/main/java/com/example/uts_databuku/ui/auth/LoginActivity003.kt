package com.example.uts_databuku.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_databuku.data.session.SessionManager003
import com.example.uts_databuku.databinding.ActivityLogin003Binding
import com.example.uts_databuku.ui.dashboard.DashboardActivity003
import com.example.uts_databuku.ui.viewmodel.AuthViewModel003
import com.example.uts_databuku.ui.viewmodel.ViewModelFactory003

class LoginActivity003 : AppCompatActivity() {

    private lateinit var binding_003: ActivityLogin003Binding
    private lateinit var sessionManager_003: SessionManager003

    private val authViewModel_003: AuthViewModel003 by viewModels {
        ViewModelFactory003.getInstance003(this)
    }

    override fun onCreate(savedInstanceState_003: Bundle?) {
        super.onCreate(savedInstanceState_003)
        binding_003 = ActivityLogin003Binding.inflate(layoutInflater)
        setContentView(binding_003.root)

        sessionManager_003 = SessionManager003(this)

        if (sessionManager_003.isLoggedIn003()) {
            navigateToDashboard003()
            return
        }

        setupListeners003()
        observeViewModel003()
    }

    private fun setupListeners003() {
        binding_003.btnLogin003.setOnClickListener {
            val username_003 = binding_003.etUsername003.text.toString().trim()
            val password_003 = binding_003.etPassword003.text.toString().trim()

            if (username_003.isEmpty()) {
                binding_003.tilUsername003.error = "Username wajib diisi!"
                return@setOnClickListener
            } else {
                binding_003.tilUsername003.error = null
            }

            if (password_003.isEmpty()) {
                binding_003.tilPassword003.error = "Password wajib diisi!"
                return@setOnClickListener
            } else {
                binding_003.tilPassword003.error = null
            }

            authViewModel_003.login003(username_003, password_003)
        }

        binding_003.tvGoToRegister003.setOnClickListener {
            startActivity(Intent(this, RegisterActivity003::class.java))
        }
    }

    private fun observeViewModel003() {
        authViewModel_003.loginResult_003.observe(this) { result_003 ->
            result_003.fold(
                onSuccess = { user_003 ->
                    sessionManager_003.createSession003(user_003.id_003, user_003.username_003)
                    Toast.makeText(this, "Login Berhasil! Selamat Datang ${user_003.username_003}", Toast.LENGTH_SHORT).show()
                    navigateToDashboard003()
                },
                onFailure = { exception_003 ->
                    Toast.makeText(this, exception_003.message ?: "Login gagal!", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun navigateToDashboard003() {
        val intent_003 = Intent(this, DashboardActivity003::class.java)
        intent_003.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent_003)
        finish()
    }
}