package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.FavouriteDrinksRepository
import com.example.drinkables.domain.entities.Drink
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadDrinksFavouriteInteractor @Inject constructor(
    private val favouriteDrinksRepository: FavouriteDrinksRepository
) {
    suspend fun run(): Flow<List<Drink>> {
        return favouriteDrinksRepository.getFavouriteDrinks()
    }
}