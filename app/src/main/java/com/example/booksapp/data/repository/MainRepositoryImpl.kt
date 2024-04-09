package com.example.booksapp.data.repository

import com.example.booksapp.data.database.UserDao
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.data.model.UserResponse
import com.example.booksapp.data.remote.ApiInterface
import com.example.booksapp.domain.repository.MainRepository

class MainRepositoryImpl(private val apiInterface: ApiInterface,private val userDao : UserDao) : MainRepository{
    override suspend fun userLogin(userModel: UserModel): Result<UserResponse> {
        val response = apiInterface.userLogin(userModel)
        if(response.isSuccessful){
            return Result.success(response.body()!!)
        }
        return Result.failure(Exception("Error"))
    }

    override suspend fun saveUserInDb(userModel: UserModel): Result<Boolean> {
        return try {
            userDao.insertUser(userModel)
            Result.success(true)
        }catch (e: Exception){
            Result.failure(e.cause!!)
        }
    }


}