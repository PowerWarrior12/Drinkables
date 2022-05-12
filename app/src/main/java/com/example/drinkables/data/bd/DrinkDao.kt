package com.example.drinkables.data.bd

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {

    @Transaction
    @Query("SELECT * FROM drink")
    fun getFavouriteDrinks(): Flow<List<DrinkEntity>>

    @Transaction
    @Query("SELECT * FROM drink WHERE :drinkId = id")
    suspend fun getFavouriteDrink(drinkId: Int): DrinkEntity?

    @Transaction
    @Query("SELECT id FROM drink")
    suspend fun getFavouriteDrinksIds(): List<Int>

    @Insert
    suspend fun addFavouriteDrink(drink: DrinkEntity)

    @Delete
    suspend fun deleteFavouriteDrink(drink: DrinkEntity)

    @Insert
    suspend fun addDrinkRating(drinkRating: DrinksRatingEntity)

    @Update
    suspend fun updateDrinkRating(drinkRating: DrinksRatingEntity)

    @Transaction
    @Query("SELECT * FROM drink_rating WHERE :id = id")
    fun getDrinkRating(id: Int): Flow<DrinksRatingEntity?>
}