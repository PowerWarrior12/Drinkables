package com.example.drinkables.data.repositories

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.lang.Exception
import javax.inject.Inject

const val APP_PREFERENCES = "com.example.drinkable"
const val USER_FIELD = "user"

class UserLocalRepositoryImpl @Inject constructor(context: Context): UserLocalRepository {

    private val sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

    override suspend fun createOrUpdateUser(userName: String): Boolean {
        return try {
            sharedPreferences.edit().putString(USER_FIELD, userName).apply()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserName(): String? {
        if (sharedPreferences.contains(USER_FIELD)) {
            return sharedPreferences.getString(USER_FIELD, "")
        }
        return null
    }
}