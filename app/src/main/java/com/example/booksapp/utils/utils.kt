package com.example.booksapp.utils

import androidx.lifecycle.MutableLiveData

object Utils {

    fun validateEmail(email: String): String? {
        return if (!email.matches(Regex(EMAIL_PATTERN))) "Invalid email format" else null
    }

    fun validateName(name: String): String? {
        return if (name.isBlank()) "Name Required" else null
    }

    fun validatePassword(password: String): String? {
        return if (password.length < MIN_PASSWORD_LENGTH) "Password must be at least $MIN_PASSWORD_LENGTH characters long" else null
    }

    fun validateConfirmPassword(
        password: String?,
        confirmPassword: String?,
        passwordError: MutableLiveData<String?>,
        confirmPasswordError: MutableLiveData<String?>
    ) {
        when {
            password.isNullOrBlank() -> passwordError.value = "Password required"
            password.isNotEmpty() && confirmPassword != password -> confirmPasswordError.value =
                "Password must be same"

            else -> confirmPasswordError.value = null
        }
    }

    fun loginSignUpClicked(
        email: String?,
        password: String?,
        confirmPassword: String?,
        name: String?,
        emailError: MutableLiveData<String?>,
        passwordError: MutableLiveData<String?>,
        confirmPasswordError: MutableLiveData<String?>,
        nameError: MutableLiveData<String?>
    ) {
        when {
            email.isNullOrBlank() -> emailError.value = "Email required"
            password.isNullOrBlank() -> passwordError.value = "Password required"
            confirmPassword.isNullOrBlank() -> confirmPasswordError.value =
                "Confirm Password required"

            name.isNullOrBlank() -> nameError.value = "Name Required"

            else -> {
                emailError.value = null
                passwordError.value = null
                confirmPasswordError.value = null
                nameError.value = null
            }
        }
    }

    private const val MIN_PASSWORD_LENGTH = 6
    private const val EMAIL_PATTERN = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
}
