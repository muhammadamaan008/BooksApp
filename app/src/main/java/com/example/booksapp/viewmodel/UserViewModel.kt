package com.example.booksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.data.model.LoginModel
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.domain.repository.MainRepository
import com.example.booksapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    // Mutable Live Data
    private val _confirmPasswordError = MutableLiveData<String?>()
    private val _emailError = MutableLiveData<String?>()
    private val _passwordError = MutableLiveData<String?>()
    val password = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val emailError: LiveData<String?> get() = _emailError
    val passwordError: LiveData<String?> get() = _passwordError
    val confirmPasswordError: LiveData<String?> get() = _confirmPasswordError

    private val loginResponse = MutableLiveData<UserModel>()
    private val userData =  MutableLiveData<UserModel>()

    fun setUserData(userModel: UserModel){
        userData.value = userModel
    }

    fun userLogin() {
        println("hello")
        viewModelScope.launch {
            userData.value?.let {
                try {
                    val response = mainRepository.userLogin(it)
                    response.onSuccess {
                            println(response.toString())
                    }.onFailure {
                        println(response.toString())
                    }
                } catch (e: Exception) {
                    println("Exception: ${e.message}")
                }
            }
        }
    }


    fun validateEmail() {
        _emailError.value = Utils.validateEmail(email = email.value.toString())
    }

    fun validatePassword() {
        _passwordError.value = Utils.validatePassword(password = password.value.toString())
    }

    fun validateConfirmPassword() {
        Utils.validateConfirmPassword(
            password.value,
            confirmPassword.value,
            _passwordError,
            _confirmPasswordError
        )
    }

    fun loginSignUpClicked() {
        Utils.loginSignUpClicked(
            email.value,
            password.value,
            confirmPassword.value,
            _emailError,
            _passwordError,
            _confirmPasswordError
        )
    }


}
