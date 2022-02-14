package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.FavouriteDrinksRepository
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class ChangeFavouriteDrinkInteractor @Inject constructor(
    private val favouriteDrinksRepository: FavouriteDrinksRepository
) {
    suspend fun run(drinkId: Int, drinks: MutableList<Drink>): MutableList<Drink> {
        updateFavouriteInStorage(drinkId)
        drinks.find { drink ->
            drink.id == drinkId
        }?.let { drink ->
            drink.favourites = !drink.favourites
        }
        return drinks
    }

    suspend fun run(drink: Drink): Drink {
        updateFavouriteInStorage(drink.id)
        drink.favourites = !drink.favourites
        return drink
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