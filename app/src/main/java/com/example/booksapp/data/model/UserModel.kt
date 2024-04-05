package com.example.booksapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UserModel(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("email")
    val email: String?= null,

    @SerializedName("token")
    val token: String?= null,

    @SerializedName("name")
    val name: String?= null,

    @SerializedName("pic")
    val pic: String?= null,

    @SerializedName("password")
    val password: String?= null
) : Serializable

data class UserResponse(
    val data: UserModel?,
    val message: String,
    val statusCode: String
)
