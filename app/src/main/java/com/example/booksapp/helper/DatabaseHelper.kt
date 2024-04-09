package com.example.booksapp.helper

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.booksapp.data.database.UserDao
import com.example.booksapp.data.model.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun userDao(): UserDao
}