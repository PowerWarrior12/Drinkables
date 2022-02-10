package com.example.drinkables.presentation.di

import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.data.api.entities.DrinksResponse
import com.example.drinkables.data.mappers.DrinkDetailedMapper
import com.example.drinkables.data.mappers.DrinkMapper
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.data.repositories.DrinksRepository
import com.example.drinkables.data.repositories.DrinksRepositoryImpl
import com.example.drinkables.domain.entities.Drink
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindModule {
    @Binds
    abstract fun bindDrinkRepository(
        drinksRepository: DrinksRepositoryImpl
    ): DrinksRepository

    @Binds
    abstract fun bindDrinkViewEntityMapper(
        drinksViewEntityMapper: DrinkMapper
    ): EntityMapper<DrinksResponse, Drink>

    @Binds
    abstract fun bindDrinkDetailedViewEntityMapper(
        drinkDetailedViewEntityMapper: DrinkDetailedMapper
    ): EntityMapper<DrinkResponse, Drink>
}