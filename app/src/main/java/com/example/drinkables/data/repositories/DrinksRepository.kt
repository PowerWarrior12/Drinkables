package com.example.drinkables.data.repositories

import androidx.paging.PagingData
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.Drink
import kotlinx.coroutines.flow.Flow

interface DrinksRepository {
    suspend fun loadDrinkDetailed(id: Int): Result<Drink>
    fun getPagingDrinks(): Flow<PagingData<Drink>>
    fun getPagingDrinksByName(name: String): Flow<PagingData<Drink>>
}