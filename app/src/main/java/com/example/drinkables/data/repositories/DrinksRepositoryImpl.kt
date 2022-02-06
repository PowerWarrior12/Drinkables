package com.example.drinkables.data.repositories

import com.example.drinkables.data.api.DrinksApi
import com.example.drinkables.data.api.entities.DrinksApiResponse
import com.example.drinkables.data.mappers.IEntityMapper
import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.domain.common.Result
import javax.inject.Inject

private const val ERROR_MESSAGE = "Error of loading"

class DrinksRepositoryImpl @Inject constructor(
    private val drinksApi: DrinksApi,
    private val mapper: IEntityMapper<DrinksApiResponse, DrinkViewEntity>
) : DrinksRepository {
    override suspend fun loadDrinks(): Result<MutableList<DrinkViewEntity>> {
        try {
            val response = drinksApi.loadDrinks()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!.map {
                    mapper.mapEntity(it)
                }.toMutableList())
            }
            return Result.Error(Exception(ERROR_MESSAGE))
        } catch (exception: Exception) {
            return Result.Error(exception)
        }
    }
}