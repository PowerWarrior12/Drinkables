package com.example.drinkables.data.api

import com.example.drinkables.data.api.entities.DrinksApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface DrinksApi {
    @GET("beers")
    suspend fun loadDrinks(): Response<MutableList<DrinksApiResponse>>
}