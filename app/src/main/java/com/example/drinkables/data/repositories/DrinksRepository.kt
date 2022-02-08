package com.example.drinkables.data.repositories

import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.Drink

interface DrinksRepository {
    suspend fun loadDrinks(): Result<MutableList<Drink>>
    suspend fun loadDrinkDetailed(id: Int): Result<Drink>
}