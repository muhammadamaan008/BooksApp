package com.example.booksapp.data.repository

import androidx.lifecycle.LiveData
import com.example.booksapp.data.database.UserDao
import com.example.booksapp.data.model.AuthorModel
import com.example.booksapp.data.model.BooksModel
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.data.model.UserResponse
import com.example.booksapp.data.remote.ApiInterface
import com.example.booksapp.domain.repository.MainRepository
import org.json.JSONException
import org.json.JSONObject

class MainRepositoryImpl(private val apiInterface: ApiInterface, private val userDao: UserDao) :
    MainRepository {
    override suspend fun userLogin(userModel: UserModel): Result<UserResponse> {
        val response = apiInterface.userLogin(userModel)
        return if (response.isSuccessful) {
            println("in repo impl ${response.body()?.message}")
            Result.success(response.body()!!)
        } else {
            val errorBodyString = response.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorBodyString ?: "").getString("message")
            } catch (e: JSONException) {
                "Unknown error occurred"
            }
            println("Error message from server: $errorMessage")
            Result.failure(Exception(errorMessage))
        }
    }

    override suspend fun saveUserInDb(userModel: UserModel): Result<Boolean> {
        return try {
            userDao.insertUser(userModel)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun userRegistration(userModel: UserModel): Result<UserResponse> {
        val response = apiInterface.userRegistration(userModel)
        return if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            val errorBodyString = response.errorBody()?.string()
            val errorMessage = try {
                // Parse the error body string to get the error message
                JSONObject(errorBodyString ?: "").getString("message")
            } catch (e: JSONException) {
                // If parsing fails, fallback to a generic error message
                "Unknown error occurred"
            }
            println("Error message from server: $errorMessage")
            Result.failure(Exception(errorMessage))
        }
    }

    override suspend fun verifyToken(userModel: UserModel): Result<UserResponse> {
        val response = apiInterface.verifyToken(userModel)
        return if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Token verification failed"))
        }
    }

    override suspend fun getAllAuthors(token: String): Result<List<AuthorModel>> {
        val response = apiInterface.getAllAuthors(token)
        return if(response.isSuccessful){
            Result.success(response.body()!!)
        }else{
            val errorBodyString = response.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorBodyString ?: "").getString("message")
            } catch (e: JSONException) {
                "Unknown error occurred"
            }
            println("Error message from server all authors: $errorMessage")
            Result.failure(Exception(errorMessage))
        }
    }

    override suspend fun getAllBooks(token: String): Result<List<BooksModel>> {
        val response = apiInterface.getAllBooks(token)
        return if(response.isSuccessful){
            Result.success(response.body()!!)
        }else{
            val errorBodyString = response.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorBodyString ?: "").getString("message")
            } catch (e: JSONException) {
                "Unknown error occurred"
            }
            println("Error message from server all books: $errorMessage")
            Result.failure(Exception(errorMessage))
        }
    }

    override suspend fun getUserData(): Result<LiveData<List<UserModel>>> {
        return try {
            Result.success(userDao.getAll())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}