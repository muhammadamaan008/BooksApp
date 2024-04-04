package com.example.booksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booksapp.utils.Utils

class UserViewModel : ViewModel() {
    // Mutable Live Data
    val password = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    private val _confirmPasswordError = MutableLiveData<String?>()
    private val _emailError = MutableLiveData<String?>()
    private val _passwordError = MutableLiveData<String?>()

    // Live Data for error messages
    val emailError: LiveData<String?> get() = _emailError
    val passwordError: LiveData<String?> get() = _passwordError
    val confirmPasswordError: LiveData<String?> get() = _confirmPasswordError

    fun validateEmail() {
        _emailError.value = Utils.validateEmail(email = email.value.toString())
//        when{
//            !email.value.toString().matches(Regex(EMAIL_PATTERN)) -> _emailError.value = "Invalid email format"
//            else -> {
//                _emailError.value = null
//            }
//        }
    }

    fun validatePassword() {
        val temp = Utils.validatePassword(password = password.value.toString())
        println(temp)
        _passwordError.value = temp
//        when{
//            password.value?.length!! < MIN_PASSWORD_LENGTH -> _passwordError.value = "Password must be at least $MIN_PASSWORD_LENGTH characters long"
//            else -> {
//                _passwordError.value = null
//            }
//        }
    }

    fun validateConfirmPassword(){
        Utils.validateConfirmPassword(
            password.value,
            confirmPassword.value,
            _passwordError,
            _confirmPasswordError
        )

//        when{
//            password.value.isNullOrBlank() -> _passwordError.value = "Password required"
//            password.value!!.isNotEmpty() && confirmPassword.value.toString() != password.value.toString() -> _confirmPasswordError.value = "Password must be same"
//            else -> {
//                _confirmPasswordError.value = null
//            }
//        }
    }

    fun loginSignUpClicked(){
        Utils.loginSignUpClicked(
            email.value,
            password.value,
            confirmPassword.value,
            _emailError,
            _passwordError,
            _confirmPasswordError
        )
//        when {
//            email.value.isNullOrBlank() -> _emailError.value = "Email required"
//            password.value.isNullOrBlank() -> _passwordError.value = "Password required"
//            confirmPassword.value.isNullOrBlank() -> _confirmPasswordError.value = "Confirm Password required"
//            else -> {
//                _emailError.value = null
//                _passwordError.value = null
//                _confirmPasswordError.value = null
//            }
//        }
    }

//    companion object {
//        private const val MIN_PASSWORD_LENGTH = 6
//        private const val EMAIL_PATTERN = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
//    }
}
