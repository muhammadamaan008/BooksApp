package com.example.booksapp.data.remote

import com.example.booksapp.data.model.UserModel
import com.example.booksapp.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("login")
    suspend fun userLogin(@Body userModel: UserModel): Response<UserResponse>

    @POST("RegisterUser")
    suspend fun userRegistration(@Body userModel: UserModel): Response<UserResponse>

    @POST("verifyToken")
    suspend fun verifyToken(@Body userModel: UserModel): Response<UserResponse>

}