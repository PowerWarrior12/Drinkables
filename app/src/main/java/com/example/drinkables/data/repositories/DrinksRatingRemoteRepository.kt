package com.example.drinkables.data.repositories

import com.example.drinkables.domain.entities.PropertyModel
import kotlinx.coroutines.flow.Flow
import com.example.drinkables.domain.common.Result

interface DrinksRatingRemoteRepository {
    fun loadAllRatings(): Flow<List<PropertyModel.PropertyUserRatingModel>>
    suspend fun addOrUpdateRating(rating: PropertyModel.PropertyRatingModel, userName: String)
    suspend fun tryToCreateUser(userName: String): Flow<Result<Boolean>>
    suspend fun updateUser(lastUserName: String, newUserName: String): Flow<Result<Boolean>>
}