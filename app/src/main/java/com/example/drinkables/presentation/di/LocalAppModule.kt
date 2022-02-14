package com.example.drinkables.presentation.di

import com.example.drinkables.data.bd.DrinkDB
import com.example.drinkables.presentation.DrinksApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalAppModule {

    @Singleton
    @Provides
    fun provideDrinkDB() : DrinkDB {
        return DrinkDB.getDatabase(DrinksApplication.INSTANCE)
    }
}