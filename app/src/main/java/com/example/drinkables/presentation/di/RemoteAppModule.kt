package com.example.drinkables.presentation.di

import com.example.drinkables.BASE_URL
import com.example.drinkables.data.api.DrinksApi
import com.example.drinkables.data.api.RetrofitService
import dagger.Module
import dagger.Provides

@Module
class RemoteAppModule {
    @Provides
    fun provideDrinksApi(): DrinksApi {
        return RetrofitService()
            .getRetrofit(BASE_URL)
            .create(DrinksApi::class.java)
    }
}