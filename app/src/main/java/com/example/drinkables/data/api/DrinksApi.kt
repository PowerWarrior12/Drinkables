package com.example.drinkables.data.api

import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.data.api.entities.DrinksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DrinksApi {
    @GET("beers")
    suspend fun loadDrinks(): Response<MutableList<DrinksResponse>>

    @GET("beers/{id}")
    suspend fun loadDrink(
        @Path("id") id: Int
    ): Response<MutableList<DrinkResponse>>
}