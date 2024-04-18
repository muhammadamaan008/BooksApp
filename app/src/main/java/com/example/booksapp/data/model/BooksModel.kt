package com.example.booksapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
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
): Serializable, Parcelable
