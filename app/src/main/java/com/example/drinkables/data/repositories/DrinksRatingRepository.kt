package com.example.drinkables.data.repositories

import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.domain.common.Result
import kotlinx.coroutines.flow.Flow

interface DrinksRatingRepository {
    fun loadDrinkRating(id: Int): Flow<Result<PropertyModel.PropertyRatingModel>>
    suspend fun addDrinkRating(drinkRating: PropertyModel.PropertyRatingModel)
    suspend fun updateDrinkRating(drinkRating: PropertyModel.PropertyRatingModel)
    suspend fun checkDrinkRating(id: Int): Boolean
}