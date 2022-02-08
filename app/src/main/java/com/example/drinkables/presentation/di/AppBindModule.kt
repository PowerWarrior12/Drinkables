package com.example.drinkables.presentation.di

import com.example.drinkables.data.api.entities.DrinksApiResponse
import com.example.drinkables.data.mappers.DrinkViewEntityMapper
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.data.repositories.DrinksRepository
import com.example.drinkables.data.repositories.DrinksRepositoryImpl
import com.example.drinkables.domain.entities.DrinkViewEntity
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
        drinksViewEntityMapper: DrinkViewEntityMapper
    ): EntityMapper<DrinksApiResponse, DrinkViewEntity>
}