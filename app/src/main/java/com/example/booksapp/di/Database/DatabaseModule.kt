package com.example.booksapp.di.Database

import android.content.Context
import androidx.room.Room
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.helper.DatabaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        DatabaseHelper::class.java,
        "UserDb"
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesDao(database: DatabaseHelper) = database.userDao()

    @Provides
    fun provideEntity() = UserModel()
}
