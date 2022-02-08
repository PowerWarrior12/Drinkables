package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.DrinksRepository
import com.example.drinkables.domain.entities.DrinkViewEntity
import javax.inject.Inject
import com.example.drinkables.domain.common.Result

class LoadDrinkDetailedInteractor @Inject constructor(
    private val repository: DrinksRepository
) {
    suspend fun run(id: Int): Result<DrinkViewEntity> {
        return repository.loadDrinkDetailed(id)
    }
}