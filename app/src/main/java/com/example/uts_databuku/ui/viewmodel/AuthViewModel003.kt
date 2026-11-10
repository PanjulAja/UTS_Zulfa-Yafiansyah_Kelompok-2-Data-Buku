package com.example.uts_databuku.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uts_databuku.data.entity.UserEntity003
import com.example.uts_databuku.data.repository.AuthRepository003
import kotlinx.coroutines.launch

class AuthViewModel003(private val authRepository_003: AuthRepository003) : ViewModel() {

    private val _loginResult_003 = MutableLiveData<Result<UserEntity003>>()
    val loginResult_003: LiveData<Result<UserEntity003>> = _loginResult_003

    private val _registerResult_003 = MutableLiveData<Result<UserEntity003>>()
    val registerResult_003: LiveData<Result<UserEntity003>> = _registerResult_003

    fun login003(username_003: String, password_003: String) {
        viewModelScope.launch {
            val result_003 = authRepository_003.loginUser003(username_003, password_003)
            _loginResult_003.value = result_003
        }
    }

    fun register003(username_003: String, password_003: String, confirmPassword_003: String) {
        viewModelScope.launch {
            if (password_003 != confirmPassword_003) {
                _registerResult_003.value = Result.failure(Exception("Konfirmasi password tidak cocok!"))
                return@launch
            }
            val result_003 = authRepository_003.registerUser003(username_003, password_003)
            _registerResult_003.value = result_003
        }
    }
}