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
import com.example.drinkables.data.bd.DrinksRatingEntity
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.entities.PropertyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

private const val ERROR_MESSAGE = "Error of loading"
private const val EMPTY_LOADING = "Empty result"
private const val NETWORK_PAGE_SIZE = 25

class DrinksRepositoryImpl @Inject constructor(
    private val drinksApi: DrinksApi,
    private val drinkViewEntityMapper: EntityMapper<DrinksResponse, Drink>,
    private val drinkDetailedViewEntityMapper: EntityMapper<DrinkResponse, Drink>,
    private val drinkToDrinkEntityMapper: EntityMapper<Drink, DrinkEntity>,
    private val drinkEntityToDrinkMapper: EntityMapper<DrinkEntity, Drink>,
    private val drinkToDrinkRatingMapper: EntityMapper<PropertyModel.PropertyRatingModel, DrinksRatingEntity>,
    private val drinkRatingEntityToPropertyRatingModelMapper: EntityMapper<DrinksRatingEntity, PropertyModel.PropertyRatingModel>,
    private val drinkDB: DrinkDB
) : DrinksRepository, FavouriteDrinksRepository, DrinksRatingRepository {
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
                DrinksPagingSource(object : DrinksPagingSource.DrinksLoader {
                    override suspend fun loadDrinks(page: Int): Response<MutableList<DrinksResponse>> {
                        return drinksApi.loadDrinksPage(page)
                    }
                })
            }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                val drink = drinkViewEntityMapper.mapEntity(response)
                drink.copy(favourites = checkFavouriteDrink(drink.id))
            }
        }
    }

    override fun getPagingDrinksByName(name: String): Flow<PagingData<Drink>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                DrinksPagingSource(object : DrinksPagingSource.DrinksLoader {
                    override suspend fun loadDrinks(page: Int): Response<MutableList<DrinksResponse>> {
                        return drinksApi.loadDrinksPageByName(page, name)
                    }
                })
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

    override fun loadDrinkRating(id: Int): Flow<Result<PropertyModel.PropertyRatingModel>> {
        val drinkRating = drinkDB.drinkDao().getDrinkRating(id)
        return drinkRating.map { drinkRatingEntity ->
            if (drinkRatingEntity == null) {
                Result.Error(java.lang.Exception(EMPTY_LOADING))
            } else {
                Result.Success(drinkRatingEntityToPropertyRatingModelMapper.mapEntity(drinkRatingEntity))
            }
        }
    }

    override suspend fun addDrinkRating(drinkRating: PropertyModel.PropertyRatingModel) {
        drinkDB.drinkDao().addDrinkRating(drinkToDrinkRatingMapper.mapEntity(drinkRating))
    }

    override suspend fun updateDrinkRating(drinkRating: PropertyModel.PropertyRatingModel) {
        drinkDB.drinkDao().updateDrinkRating(drinkToDrinkRatingMapper.mapEntity(drinkRating))
    }

    override suspend fun checkDrinkRating(id: Int): Boolean {
        return drinkDB.drinkDao().getDrinkRating(id) != null
    }
}