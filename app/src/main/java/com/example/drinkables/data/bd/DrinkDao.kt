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

    @Insert
    suspend fun addUsersDrinkRatings(ratings: List<UserDrinkRatingEntity>)

    @Transaction
    @Query("SELECT * FROM user_drink_rating WHERE :drinkId = drinkId")
    suspend fun getAllDrinkRatingsByDrink(drinkId: Int): List<UserDrinkRatingEntity>

    @Transaction
    @Query("SELECT * FROM user_drink_rating WHERE :user = name")
    suspend fun getAllDrinkRatingsByUser(user: String): List<UserDrinkRatingEntity>

    @Query("DELETE FROM user_drink_rating")
    fun deleteAllUsersRatingTable()
}