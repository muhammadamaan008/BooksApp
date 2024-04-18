package com.example.booksapp.application

import android.app.Application
import com.example.booksapp.data.local.SharedPreferencesManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BooksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
    }
}