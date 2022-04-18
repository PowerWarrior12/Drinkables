package com.example.drinkables.domain.interactors

import androidx.paging.PagingData
import com.example.drinkables.data.repositories.DrinksRepository
import com.example.drinkables.domain.entities.Drink
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadPagingDrinksInteractor @Inject constructor(
    private val repository: DrinksRepository
) {
    fun run(name: String = ""): Flow<PagingData<Drink>> {
        if (name.isEmpty()) {
            return repository.getPagingDrinks()
        }
        return repository.getPagingDrinksByName(name)
    }
}