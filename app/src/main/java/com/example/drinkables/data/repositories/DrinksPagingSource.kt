package com.example.drinkables.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.drinkables.data.api.DrinksApi
import com.example.drinkables.data.api.entities.DrinksResponse

private const val STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 25
private const val ERROR_MESSAGE = "Error of loading"

class DrinksPagingSource(
    private val drinksApi: DrinksApi
) : PagingSource<Int, DrinksResponse>() {
    override fun getRefreshKey(state: PagingState<Int, DrinksResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DrinksResponse> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        try {
            val response = drinksApi.loadDrinksPage(page = pageIndex)
            if (response.isSuccessful) {
                val drinks = response.body() ?: mutableListOf()

                val nextKey =
                    if (drinks.isEmpty()) {
                        null
                    } else {
                        pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                    }
                return LoadResult.Page(
                    data = drinks,
                    prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                    nextKey = nextKey
                )
            }
            return LoadResult.Error(
                throwable = Exception(ERROR_MESSAGE)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(
                throwable = exception
            )
        }
    }
}