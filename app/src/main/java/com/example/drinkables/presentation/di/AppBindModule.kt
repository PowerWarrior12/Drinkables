package com.example.drinkables.presentation.di

import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.data.api.entities.DrinksResponse
import com.example.drinkables.data.bd.DrinkEntity
import com.example.drinkables.data.mappers.*
import com.example.drinkables.data.repositories.DrinksRepository
import com.example.drinkables.data.repositories.DrinksRepositoryImpl
import com.example.drinkables.data.repositories.FavouriteDrinksRepository
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
    abstract fun bindFavouriteDrinkRepository(
        drinksRepository: DrinksRepositoryImpl
    ): FavouriteDrinksRepository

    @Binds
    abstract fun bindDrinkViewEntityMapper(
        drinksViewEntityMapper: DrinkMapper
    ): EntityMapper<DrinksResponse, Drink>

    @Binds
    abstract fun bindDrinkDetailedViewEntityMapper(
        drinkDetailedViewEntityMapper: DrinkDetailedMapper
    ): EntityMapper<DrinkResponse, Drink>

    @Binds
    abstract fun bindDrinkToDrinkEntityMapper(
        drinkToDrinkEntityMapper: DrinkToDrinkEntityMapper
    ): EntityMapper<Drink, DrinkEntity>

    @Binds
    abstract fun bindDrinkEntityToDrinkMapper(
        drinkEntityToDrinkMapper: DrinkEntityToDrinkMapper
    ): EntityMapper<DrinkEntity, Drink>
}