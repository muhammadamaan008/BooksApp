package com.example.booksapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksapp.data.model.UserModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userModel: UserModel)

    @Query("SELECT * FROM UserModel")
    fun getAll(): LiveData<List<UserModel>>

}