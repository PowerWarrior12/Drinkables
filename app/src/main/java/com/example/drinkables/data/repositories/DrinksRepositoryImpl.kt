package com.example.drinkables.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.drinkables.data.api.DrinksApi
import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.data.api.entities.DrinksResponse
import com.example.drinkables.data.bd.DrinkDB
import com.example.drinkables.data.bd.DrinkEntity
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.Drink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ERROR_MESSAGE = "Error of loading"
private const val NETWORK_PAGE_SIZE = 25

class DrinksRepositoryImpl @Inject constructor(
    private val drinksApi: DrinksApi,
    private val drinkViewEntityMapper: EntityMapper<DrinksResponse, Drink>,
    private val drinkDetailedViewEntityMapper: EntityMapper<DrinkResponse, Drink>,
    private val drinkToDrinkEntityMapper: EntityMapper<Drink, DrinkEntity>,
    private val drinkEntityToDrinkMapper: EntityMapper<DrinkEntity, Drink>,
    private val drinkDB: DrinkDB
) : DrinksRepository, FavouriteDrinksRepository {
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

    override fun getPagingDrinks(): Flow<PagingData<Drink>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                DrinksPagingSource(drinksApi = drinksApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                val drink = drinkViewEntityMapper.mapEntity(response)
                drink.copy(favourites = checkFavouriteDrink(drink.id))
            }
        }
    }

    override suspend fun getFavouritesDrinkIds(): List<Int> {
        return drinkDB.drinkDao().getFavouriteDrinksIds()
    }

    override suspend fun getFavouriteDrinks(): Flow<List<Drink>> {
        return drinkDB.drinkDao().getFavouriteDrinks().map { drinks ->
            drinks.map { drink ->
                drinkEntityToDrinkMapper.mapEntity(drink)
            }
        }
    }

    override suspend fun checkFavouriteDrink(drinkId: Int): Boolean {
        return drinkDB.drinkDao().getFavouriteDrink(drinkId) != null
    }

    override suspend fun addFavouriteDrink(drink: Drink) {
        drinkDB.drinkDao().addFavouriteDrink(drinkToDrinkEntityMapper.mapEntity(drink))
    }

    override suspend fun deleteFavouriteDrink(drink: Drink) {
        drinkDB.drinkDao().deleteFavouriteDrink(drinkToDrinkEntityMapper.mapEntity(drink))
    }
}