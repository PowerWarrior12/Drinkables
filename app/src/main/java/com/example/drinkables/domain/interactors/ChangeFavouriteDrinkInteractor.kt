package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.FavouriteDrinksRepository
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class ChangeFavouriteDrinkInteractor @Inject constructor(
    private val favouriteDrinksRepository: FavouriteDrinksRepository
) {

    suspend fun run(drinkId: Int) {
        updateFavouriteInStorage(drinkId)
    }

    suspend fun run(drink: Drink): Drink {
        updateFavouriteInStorage(drink.id)
        return drink.copy(favourites = !drink.favourites)
    }

    private suspend fun updateFavouriteInStorage(drinkId: Int) {
        val isFavourite = favouriteDrinksRepository.checkFavouriteDrink(drinkId)
        if (isFavourite) {
            favouriteDrinksRepository.deleteFavouriteDrink(drinkId)
        } else {
            favouriteDrinksRepository.addFavouriteDrink(drinkId)
        }
    }
}