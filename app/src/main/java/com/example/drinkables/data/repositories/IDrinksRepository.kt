package com.example.drinkables.data.repositories

import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.domain.common.Result

interface IDrinksRepository {
    suspend fun loadDrinks(): Result<MutableList<DrinkViewEntity>>
}