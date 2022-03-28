package com.example.drinkables.data.repositories

import com.example.drinkables.domain.entities.Drink

interface FavouriteDrinksRepository {
    suspend fun getFavouritesDrinkIds(): List<Int>
    suspend fun getFavouriteDrinks(): List<Drink>
    suspend fun checkFavouriteDrink(drinkId: Int): Boolean
    suspend fun addFavouriteDrink(drink: Drink)
    suspend fun deleteFavouriteDrink(drink: Drink)
}