package com.example.booksapp.domain.repository

import com.example.booksapp.data.model.AuthorModel
import com.example.booksapp.data.model.BooksModel
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.data.model.UserResponse

interface MainRepository {
    suspend fun userLogin(userModel: UserModel): Result<UserResponse>
    suspend fun saveUserInDb(userModel: UserModel): Result<Boolean>
    suspend fun userRegistration(userModel: UserModel): Result<UserResponse>
    suspend fun verifyToken(userModel: UserModel): Result<UserResponse>
    suspend fun getAllAuthors(token: String): Result<List<AuthorModel>>
    suspend fun getAllBooks(token: String): Result<List<BooksModel>>
}