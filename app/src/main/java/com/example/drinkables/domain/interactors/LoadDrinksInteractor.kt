package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.DrinksRepository
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.common.Result
import javax.inject.Inject

class LoadDrinksInteractor @Inject constructor(private val repository: DrinksRepository) {
    suspend fun run(): Result<MutableList<Drink>> {
        return repository.loadDrinks()
    }
}