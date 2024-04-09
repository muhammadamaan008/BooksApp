package com.example.booksapp.data.local

import android.content.Context
import android.content.SharedPreferences;

object SharedPreferencesManager{

    private const val PREF_NAME = "booksAppPrefer"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getToken(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key,defaultValue)
    }
}