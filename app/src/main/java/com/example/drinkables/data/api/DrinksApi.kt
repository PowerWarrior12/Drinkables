package com.example.drinkables.data.api

import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.data.api.entities.DrinksResponse
import okhttp3.internal.addHeaderLenient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DrinksApi {
    @GET("beers/{id}")
    suspend fun loadDrink(
        @Path("id") id: Int
    ): Response<MutableList<DrinkResponse>>

    @GET("beers?")
    suspend fun loadDrinksPage(
        @Query("page") page: Int,
    ): Response<MutableList<DrinksResponse>>

    @GET("beers?")
    suspend fun loadDrinksPageByName(
        @Query("page") page: Int,
        @Query("beer_name") name: String
    ): Response<MutableList<DrinksResponse>>
}