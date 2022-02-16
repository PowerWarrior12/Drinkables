package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.FavouriteDrinksRepository
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class UpdateDrinkFavouriteInteractor @Inject constructor(
    private val favouriteDrinksRepository: FavouriteDrinksRepository
) {
    suspend fun run(drink: Drink): Drink {
        return drink.copy(favourites = favouriteDrinksRepository.checkFavouriteDrink(drink.id))
    }
}