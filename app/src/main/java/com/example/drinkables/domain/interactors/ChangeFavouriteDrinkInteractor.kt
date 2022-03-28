package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.FavouriteDrinksRepository
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class ChangeFavouriteDrinkInteractor @Inject constructor(
    private val favouriteDrinksRepository: FavouriteDrinksRepository
) {
    suspend fun run(drink: Drink): Drink {
        updateFavouriteInStorage(drink)
        return drink.copy(favourites = !drink.favourites)
    }

    private suspend fun updateFavouriteInStorage(drink: Drink) {
        val isFavourite = favouriteDrinksRepository.checkFavouriteDrink(drink.id)
        if (isFavourite) {
            favouriteDrinksRepository.deleteFavouriteDrink(drink)
        } else {
            favouriteDrinksRepository.addFavouriteDrink(drink)
        }
    }
}