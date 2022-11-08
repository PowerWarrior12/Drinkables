package com.example.drinkables.data.repositories

interface UserLocalRepository {
    suspend fun createOrUpdateUser(userName: String): Boolean
    suspend fun getUserName(): String?
}