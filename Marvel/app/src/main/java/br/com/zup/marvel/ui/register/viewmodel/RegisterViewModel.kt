package br.com.zup.marvel.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.*
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.domain.repository.AuthRepository

class RegisterViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDate(user: User) {
        when {
            user.name.isEmpty() -> {
                _errorState.value = NAME_ERROR_MESSAGE
            }
            user.name.length in 1 .. 2 -> {
                _errorState.value = NAME_ERROR_LENGTH_MESSAGE
            }
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            user.password.length in 1 .. 7 -> {
                _errorState.value = PASSWORD_ERROR_LENGTH_MESSAGE
            }
            else -> {
                registerUser(user)
            }
        }
    }

    private fun registerUser(user: User) {
        try {
            authRepository.registerUser(
                user.email,
                user.password
            ).addOnSuccessListener { task ->
                authRepository.updateUserName(user.name)?.addOnSuccessListener {
                    _registerState.value = user
                }
            }.addOnFailureListener {
                _errorState.value = CREATE_USER_ERROR_MESSAGE
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}