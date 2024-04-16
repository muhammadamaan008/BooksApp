package com.example.booksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.utils.EventWrapper
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


    private val _navigationListener = MutableLiveData<EventWrapper<Boolean>?>()
    val navigationListener: LiveData<EventWrapper<Boolean>?> get() = _navigationListener

    private val _toastMessage = MutableLiveData<EventWrapper<String>?>()
    val toastMessage: LiveData<EventWrapper<String>?> get() = _toastMessage

    private val _loadingBar = MutableLiveData<Boolean?>()
    val loadingBar: LiveData<Boolean?> get() = _loadingBar

    private fun setUserData(userModel: UserModel) {
        userData.value = userModel
    }

    fun checkValidationAndLogin(userModel: UserModel) {
        if (passwordError.value.isNullOrBlank() && emailError.value.isNullOrBlank()) {
            setUserData(userModel)
            userLogin()
        }
    }

    fun checkValidationAndRegister(userModel: UserModel) {
        if (passwordError.value.isNullOrBlank() && emailError.value.isNullOrBlank() && nameError.value.isNullOrBlank() && confirmPasswordError.value.isNullOrBlank()) {
            setUserData(userModel)
            userRegistration()
        }
    }

    private fun userLogin() {
        set(true, null, null)
        viewModelScope.launch {
            userData.value?.let { userModel ->
                try {
                    val response = mainRepository.userLogin(userModel)
                    response.onSuccess { userResponse ->
                        userResponse.data?.apply {
                            saveTokenInSharedPreferences(token!!)
                            saveUserInDb(this)
                            set(
                                false,
                                EventWrapper(userResponse.message.toString()),
                                EventWrapper(true)
                            )
                        }
                    }.onFailure {
                        set(false, EventWrapper(it.message.toString()), EventWrapper(false))
                    }
                } catch (e: Exception) {
                    set(false, EventWrapper(e.message.toString()), EventWrapper(false))
                }
            }
        }
    }

    private fun userRegistration() {
        set(true, null, null)
        viewModelScope.launch {
            userData.value?.let { userModel ->
                try {
                    val response = mainRepository.userRegistration(userModel)
                    response.onSuccess { userResponse ->
                        userResponse.data?.apply {
                            set(
                                false,
                                EventWrapper(userResponse.message.toString()),
                                EventWrapper(true)
                            )
                        }
                    }.onFailure {
                        set(false, EventWrapper(it.message.toString()), EventWrapper(false))
                    }
                } catch (e: Exception) {
                    set(false, EventWrapper(e.message.toString()), EventWrapper(false))
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

    private fun set(
        loading: Boolean?,
        toastMessage: EventWrapper<String>?,
        navigate: EventWrapper<Boolean>?
    ) {
        _loadingBar.value = loading
        _toastMessage.value = toastMessage
        _navigationListener.value = navigate
    }


    private fun saveUserInDb(userModel: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val isUserInserted = mainRepository.saveUserInDb(userModel)
            if (isUserInserted.isSuccess) {
                println("Saved In Db")
            } else {
                println("Not Saved in Db")
            }
        }
    }

    suspend fun verifyToken(userModel: UserModel): Boolean {
        var isTokenVerified = false
        println("tokennnn ${SharedPreferencesManager.getToken("TOKEN", null)}")
        viewModelScope.launch(Dispatchers.IO) {
            val verifyTokenResponse = mainRepository.verifyToken(userModel)
            if (verifyTokenResponse.isSuccess) {
                println("token verified")
                isTokenVerified = true
            }
            println("error while verifying token")
        }

        return isTokenVerified
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
            name.value,
            _emailError,
            _passwordError,
            _confirmPasswordError,
            _nameError
        )
    }
}
