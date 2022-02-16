package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.FavouriteDrinksRepository
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class ChangeFavouriteDrinkInteractor @Inject constructor(
    private val favouriteDrinksRepository: FavouriteDrinksRepository
) {
    suspend fun run(drinkId: Int, drinks: MutableList<Drink>): MutableList<Drink> {
        updateFavouriteInStorage(drinkId)
        val currentDrink = drinks.find { drink ->
            drink.id == drinkId
        }
        if (currentDrink != null) {
            drinks[drinks.indexOf(currentDrink)] =
                currentDrink.copy(favourites = !currentDrink.favourites)
        }
        return drinks
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