package com.example.booksapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginModel(
    @SerializedName("email")
    val email: String?= null,

    @SerializedName("password")
    val password: String?= null
) : Serializable
