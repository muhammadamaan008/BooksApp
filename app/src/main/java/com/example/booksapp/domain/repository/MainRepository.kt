package com.example.booksapp.domain.repository

import com.example.booksapp.data.model.LoginModel
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.data.model.UserResponse
import retrofit2.Response

interface MainRepository {
    suspend fun userLogin(userModel: UserModel) : Result<UserResponse>
}