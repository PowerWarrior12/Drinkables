package com.example.drinkables.data.repositories

import com.example.drinkables.data.api.DrinksApi
import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.data.api.entities.DrinksResponse
import com.example.drinkables.data.bd.DrinkDB
import com.example.drinkables.data.bd.DrinkEntity
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.presentation.DrinksApplication
import javax.inject.Inject

private const val ERROR_MESSAGE = "Error of loading"

class DrinksRepositoryImpl @Inject constructor(
    private val drinksApi: DrinksApi,
    private val drinkViewEntityMapper: EntityMapper<DrinksResponse, Drink>,
    private val drinkDetailedViewEntityMapper: EntityMapper<DrinkResponse, Drink>,
    private val drinkDB: DrinkDB
) : DrinksRepository, FavouriteDrinksRepository {
    override suspend fun loadDrinks(): Result<MutableList<Drink>> {
        try {
            val response = drinksApi.loadDrinks()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!.map {
                    drinkViewEntityMapper.mapEntity(it)
                }.toMutableList())
            }
            return Result.Error(Exception(ERROR_MESSAGE))
        } catch (exception: Exception) {
            return Result.Error(exception)
        }
    }

    override suspend fun loadDrinkDetailed(id: Int): Result<Drink> {
        try {
            val response = drinksApi.loadDrink(id)
            if (response.isSuccessful) {
                return Result.Success(drinkDetailedViewEntityMapper.mapEntity(response.body()!![0]))
            }
            return Result.Error(Exception(ERROR_MESSAGE))
        } catch (exception: Exception) {
            return Result.Error(exception)
        }
    }

    override suspend fun getFavouritesDrinksId(): List<Int> {
        return drinkDB.drinkDao().getFavouriteDrinks().map { drink ->
            drink.id
        }
    }

    override suspend fun checkFavouriteDrink(drinkId: Int): Boolean {
        drinkDB.drinkDao().getFavouriteDrink(drinkId)?.let {
            return true
        }
        return false
    }

    override suspend fun addFavouriteDrink(drinkId: Int) {
        drinkDB.drinkDao().addFavouriteDrink(DrinkEntity(drinkId))
    }

    override suspend fun deleteFavouriteDrink(drinkId: Int) {
        drinkDB.drinkDao().deleteFavouriteDrink(DrinkEntity(drinkId))
    }
}