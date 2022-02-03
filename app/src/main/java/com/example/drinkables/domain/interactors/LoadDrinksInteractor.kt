package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.IDrinksRepository
import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.domain.common.Result
import javax.inject.Inject

class LoadDrinksInteractor @Inject constructor(private val repository: IDrinksRepository) {
    suspend fun run(): Result<MutableList<DrinkViewEntity>> {
        return repository.loadDrinks()
    }
}