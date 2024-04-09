package com.example.booksapp.domain.repository

import com.example.booksapp.data.model.UserModel
import com.example.booksapp.data.model.UserResponse

interface MainRepository {
    suspend fun userLogin(userModel: UserModel) : Result<UserResponse>
    suspend fun saveUserInDb(userModel: UserModel): Result<Boolean>
}