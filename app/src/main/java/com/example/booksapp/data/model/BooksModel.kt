package com.example.booksapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BooksModel(
    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("id")
    val bookId: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("about")
    val about: String? = null,

    @SerializedName("url")
    val pic: String? = null,
): Serializable
