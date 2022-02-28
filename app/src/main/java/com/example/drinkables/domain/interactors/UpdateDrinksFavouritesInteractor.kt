package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.FavouriteDrinksRepository
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class UpdateDrinksFavouritesInteractor @Inject constructor(
    private val favouriteDrinksRepository: FavouriteDrinksRepository
) {
    suspend fun run(drinks: MutableList<Drink>): MutableList<Drink> {
        val favourites = favouriteDrinksRepository.getFavouritesDrinkIds()
        drinks.forEach { drink ->
            val drinkIndex = drinks.indexOf(drink)
            drinks[drinkIndex] = drink.copy(favourites = favourites.contains(drink.id))
        }
        return drinks
    }
}