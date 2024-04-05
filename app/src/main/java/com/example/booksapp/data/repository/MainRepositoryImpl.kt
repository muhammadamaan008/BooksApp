package com.example.booksapp.data.repository

import com.example.booksapp.data.model.UserModel
import com.example.booksapp.data.model.UserResponse
import com.example.booksapp.data.remote.ApiInterface
import com.example.booksapp.domain.repository.MainRepository

class MainRepositoryImpl(private val apiInterface: ApiInterface) : MainRepository{
    override suspend fun userLogin(userModel: UserModel): Result<UserResponse> {
        val temp = apiInterface.userLogin(userModel)
        if(temp.isSuccessful){
            return Result.success(temp.body()!!)
        }
        return Result.failure(Exception("Error"))
    }
}