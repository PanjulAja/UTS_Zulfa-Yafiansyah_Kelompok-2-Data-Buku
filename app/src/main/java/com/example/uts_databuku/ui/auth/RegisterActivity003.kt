package com.example.uts_databuku.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_databuku.databinding.ActivityRegister003Binding
import com.example.uts_databuku.ui.viewmodel.AuthViewModel003
import com.example.uts_databuku.ui.viewmodel.ViewModelFactory003

class RegisterActivity003 : AppCompatActivity() {

    private lateinit var binding_003: ActivityRegister003Binding

    private val authViewModel_003: AuthViewModel003 by viewModels {
        ViewModelFactory003.getInstance003(this)
    }

    override fun onCreate(savedInstanceState_003: Bundle?) {
        super.onCreate(savedInstanceState_003)
        binding_003 = ActivityRegister003Binding.inflate(layoutInflater)
        setContentView(binding_003.root)

        setupListeners003()
        observeViewModel003()
    }

    private fun setupListeners003() {
        binding_003.btnRegister003.setOnClickListener {
            val username_003 = binding_003.etRegUsername003.text.toString().trim()
            val password_003 = binding_003.etRegPassword003.text.toString().trim()
            val confirmPassword_003 = binding_003.etRegConfirmPassword003.text.toString().trim()

            var isValid_003 = true

            if (username_003.isEmpty()) {
                binding_003.tilRegUsername003.error = "Username wajib diisi!"
                isValid_003 = false
            } else {
                binding_003.tilRegUsername003.error = null
            }

            if (password_003.length < 6) {
                binding_003.tilRegPassword003.error = "Password minimal 6 karakter!"
                isValid_003 = false
            } else {
                binding_003.tilRegPassword003.error = null
            }

            if (confirmPassword_003 != password_003) {
                binding_003.tilRegConfirmPassword003.error = "Konfirmasi password tidak cocok!"
                isValid_003 = false
            } else {
                binding_003.tilRegConfirmPassword003.error = null
            }

            if (isValid_003) {
                authViewModel_003.register003(username_003, password_003, confirmPassword_003)
            }
        }

        binding_003.tvGoToLogin003.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel003() {
        authViewModel_003.registerResult_003.observe(this) { result_003 ->
            result_003.fold(
                onSuccess = { user_003 ->
                    Toast.makeText(
                        this,
                        "Registrasi berhasil! Silakan login dengan username: ${user_003.username_003}",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this, LoginActivity003::class.java))
                    finish()
                },
                onFailure = { exception_003 ->
                    Toast.makeText(this, exception_003.message ?: "Registrasi gagal!", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}