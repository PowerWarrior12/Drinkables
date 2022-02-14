package com.example.drinkables.data.bd

import androidx.room.*

@Dao
interface DrinkDao {

    @Transaction
    @Query("SELECT * FROM drink")
    suspend fun getFavouriteDrinks(): List<DrinkEntity>

    @Transaction
    @Query("SELECT * FROM drink WHERE :drinkId = id")
    suspend fun getFavouriteDrink(drinkId : Int) : DrinkEntity?

    @Insert
    suspend fun addFavouriteDrink(drink: DrinkEntity)

    @Delete
    suspend fun deleteFavouriteDrink(drink: DrinkEntity)
}