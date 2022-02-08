package com.example.drinkables.data.repositories

import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.domain.common.Result

interface DrinksRepository {
    suspend fun loadDrinks(): Result<MutableList<DrinkViewEntity>>
    suspend fun loadDrinkDetailed(id: Int): Result<DrinkViewEntity>
}