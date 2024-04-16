package com.example.booksapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(indices = [Index(value = ["name"], unique = true)])
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("token")
    val token: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("pic")
    val pic: String? = null,

    @SerializedName("password")
    val password: String? = null
) : Serializable

data class UserResponse(
    val data: UserModel?,
    val message: String? = null,
    val statusCode: String? = null,
    val success: Boolean?
)
