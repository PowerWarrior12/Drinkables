package com.example.drinkables.data.repositories

interface FavouriteDrinksRepository {
    suspend fun getFavouritesDrinkids(): List<Int>
    suspend fun checkFavouriteDrink(drinkId: Int): Boolean
    suspend fun addFavouriteDrink(drinkId: Int)
    suspend fun deleteFavouriteDrink(drinkId: Int)
}