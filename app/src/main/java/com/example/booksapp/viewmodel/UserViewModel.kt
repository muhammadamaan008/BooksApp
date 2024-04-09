package com.example.booksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.data.local.SharedPreferencesManager
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.domain.repository.MainRepository
import com.example.booksapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    // Mutable Live Data
    private val _confirmPasswordError = MutableLiveData<String?>()
    private val _emailError = MutableLiveData<String?>()
    private val _passwordError = MutableLiveData<String?>()
    private val _nameError = MutableLiveData<String?>()

    val password = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val emailError: LiveData<String?> get() = _emailError
    val passwordError: LiveData<String?> get() = _passwordError
    val confirmPasswordError: LiveData<String?> get() = _confirmPasswordError
    val nameError: LiveData<String?> get() = _nameError

    private val userData = MutableLiveData<UserModel>()

    private fun setUserData(userModel: UserModel) {
        userData.value = userModel
    }

    fun checkValidationAndLogin(userModel: UserModel) {
        if (passwordError.value.isNullOrBlank() && emailError.value.isNullOrBlank()) {
            setUserData(userModel)
            userLogin()
        }
    }

    fun checkValidationAndRegister(userModel: UserModel){
        if (passwordError.value.isNullOrBlank() && emailError.value.isNullOrBlank() && nameError.value.isNullOrBlank() && confirmPasswordError.value.isNullOrBlank()) {
            setUserData(userModel)
            userRegistration()
        }
    }

    private fun userLogin() {
        println("hello")
        println(passwordError.value.toString())
        viewModelScope.launch {
            userData.value?.let { userModel ->
                try {
                    val response = mainRepository.userLogin(userModel)
                    response.onSuccess { userResponse ->
                        println(userResponse.data.toString())
                        userResponse.data?.apply {
                            token?.let { token ->
                                saveTokenInSharedPreferences(token)
                            }
                            saveUserInDb(this)
                        }
                    }.onFailure { throwable ->
                        println("Error occurred: ${throwable.message}")
                    }
                } catch (e: Exception) {
                    println("Exception: ${e.message}")
                }
            }
        }
    }

    private fun userRegistration() {
        println("Registration")
        viewModelScope.launch {
            userData.value?.let { userModel ->
                try {
                    val response = mainRepository.userRegistration(userModel)
                    response.onSuccess { userResponse ->
                        println(userResponse.data.toString())
                        userResponse.data?.apply {
                            println("hurrah registeredddddd")
                        }
                    }.onFailure { throwable ->
                        println("ooppssss not registeredddddd")
                        println("Error occurred: ${throwable.message}")
                    }
                } catch (e: Exception) {
                    println("Exception: ${e.message}")
                }
            }
        }
    }

    private fun saveTokenInSharedPreferences(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            SharedPreferencesManager.saveToken(
                "TOKEN",
                token
            )
        }
    }

    private fun saveUserInDb(userModel: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val isUserInserted = mainRepository.saveUserInDb(userModel)
            if (isUserInserted.isSuccess) {
                println("okkkkk")
            } else {
                println("not okkkkk")
            }
        }
    }

    fun validateEmail() {
        _emailError.value = Utils.validateEmail(email = email.value.toString())
    }

//    fun validateName() {
//        _nameError.value = Utils.validateName(name = name.value.toString())
//    }

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
            name.value,
            _emailError,
            _passwordError,
            _confirmPasswordError,
            _nameError
        )
    }
}
