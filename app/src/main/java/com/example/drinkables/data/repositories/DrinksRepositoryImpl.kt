package com.example.drinkables.data.repositories

import com.example.drinkables.data.api.DrinksApi
import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.data.api.entities.DrinksResponse
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

private const val ERROR_MESSAGE = "Error of loading"

class DrinksRepositoryImpl @Inject constructor(
    private val drinksApi: DrinksApi,
    private val drinkViewEntityMapper: EntityMapper<DrinksResponse, Drink>,
    private val drinkDetailedViewEntityMapper: EntityMapper<DrinkResponse, Drink>
) : DrinksRepository {
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
}