package com.example.drinkables.data.api

import com.example.drinkables.data.api.entities.DrinkDetailedApiResponse
import com.example.drinkables.data.api.entities.DrinksApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DrinksApi {
    @GET("beers")
    suspend fun loadDrinks(): Response<MutableList<DrinksApiResponse>>

    @GET("beers/{id}")
    suspend fun loadDrink(
        @Path("id") id: Int
    ): Response<MutableList<DrinkDetailedApiResponse>>
}