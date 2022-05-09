package com.example.drinkables.presentation.di

import android.content.Context
import com.example.drinkables.presentation.DrinksApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RemoteAppModule::class, AppBindModule::class, NavigationModule::class, LocalAppModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideContext(): Context {
        return DrinksApplication.INSTANCE
    }
}