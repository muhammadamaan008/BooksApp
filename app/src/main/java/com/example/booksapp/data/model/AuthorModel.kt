package com.example.booksapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthorModel(
    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("pic")
    val pic: String? = null,

//    @SerializedName("_v")
//    val v: Int?
): Serializable
