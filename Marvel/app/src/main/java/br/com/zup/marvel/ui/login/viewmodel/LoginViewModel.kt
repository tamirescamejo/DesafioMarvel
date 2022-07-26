package br.com.zup.marvel.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.EMAIL_ERROR_MESSAGE
import br.com.zup.marvel.LOGIN_ERROR_MESSAGE
import br.com.zup.marvel.PASSWORD_ERROR_MESSAGE
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.domain.repository.AuthRepository

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private var _loginState = MutableLiveData<User>()
    val loginState: LiveData<User> = _loginState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun getCurrentUser() = authRepository.getCurrentUser()

    private fun loginUser(user: User) {
        try {
            authRepository.loginUser(user.email, user.password).addOnSuccessListener { task ->
                _loginState.value = user
            }.addOnFailureListener {
                _errorState.value = LOGIN_ERROR_MESSAGE
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }

    fun validateDate(user: User) {
        when {
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                loginUser(user)
            }
        }
    }
}