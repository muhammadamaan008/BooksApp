package com.example.booksapp.utils

import androidx.lifecycle.MutableLiveData
import com.example.booksapp.viewmodel.UserViewModel

object Utils {

    fun validateEmail(email: String): String? {
        return if (!email.matches(Regex(EMAIL_PATTERN))) "Invalid email format" else null
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
            password.isNotEmpty() && confirmPassword != password -> confirmPasswordError.value = "Password must be same"
            else -> confirmPasswordError.value = null
        }
    }

//    fun validateConfirmPassword(password: String, confirmPassword: String): String? {
//        return if (password.isBlank()) "Password required" else if(password != confirmPassword) "Password must be same" else null
//    }

//    fun passwordNullCheck(password: String): String?{
//        return if(password.isBlank()) "Password required" else null
//    }
//
//    fun emailNullCheck(email: String): String?{
//        return if(email.isBlank()) "Email required" else null
//    }
//
//
//    fun confirmPasswordNullCheck(confirmPassword: String): String?{
//        return if(confirmPassword.isBlank()) "Confirm Password required" else null
//    }

    fun loginSignUpClicked(
        email: String?,
        password: String?,
        confirmPassword: String?,
        emailError: MutableLiveData<String?>,
        passwordError: MutableLiveData<String?>,
        confirmPasswordError: MutableLiveData<String?>
    ) {
        when {
            email.isNullOrBlank() -> emailError.value = "Email required"
            password.isNullOrBlank() -> passwordError.value = "Password required"
            confirmPassword.isNullOrBlank() -> confirmPasswordError.value = "Confirm Password required"
            else -> {
                emailError.value = null
                passwordError.value = null
                confirmPasswordError.value = null
            }
        }
    }

//    fun loginSignUpClicked(email: String, password: String, confirmPassword: String): Triple<String?, String?, String?> {
//        println("ok")
//        return if(email.isBlank()){
//            Triple("Email required",null,null)
//        }else if( password.isBlank()){
//            Triple(null,"Password required",null)
//        }else if(confirmPassword.isBlank()){
//            Triple(null,null,"Confirm Password required")
//        }else{
//            Triple(null,null,null)
//        }


//        return Triple(
//            if (email.isEmpty()) "Email required" else null,
//            if (password.isBlank()) "Password required" else null,
//            if (confirmPassword.isBlank()) "Confirm Password required" else null
//        )
//    }

    private const val MIN_PASSWORD_LENGTH = 6
    private const val EMAIL_PATTERN = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
}
