package com.example.drinkables.data.repositories

import com.example.drinkables.domain.entities.UsersRatings
import kotlinx.coroutines.flow.Flow

interface DrinksRatingRemoteRepository {
    fun loadAllRatings(): Flow<List<UsersRatings>>
    suspend fun addOrUpdateRating(rating: UsersRatings)
}