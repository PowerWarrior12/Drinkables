package com.example.drinkables.data.repositories

import com.example.drinkables.data.api.DrinksApi
import com.example.drinkables.data.api.entities.DrinkDetailedApiResponse
import com.example.drinkables.data.api.entities.DrinksApiResponse
import com.example.drinkables.data.mappers.DrinkDetailedViewEntityMapper
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.domain.common.Result
import javax.inject.Inject

private const val ERROR_MESSAGE = "Error of loading"

class DrinksRepositoryImpl @Inject constructor(
    private val drinksApi: DrinksApi,
    private val drinkViewEntityMapper: EntityMapper<DrinksApiResponse, DrinkViewEntity>,
    private val drinkDetailedViewEntityMapper: EntityMapper<DrinkDetailedApiResponse, DrinkViewEntity>
) : DrinksRepository {
    override suspend fun loadDrinks(): Result<MutableList<DrinkViewEntity>> {
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

    override suspend fun loadDrinkDetailed(id: Int): Result<DrinkViewEntity> {
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
}