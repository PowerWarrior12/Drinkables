package com.example.drinkables.presentation.di

import dagger.Module

@Module(includes = [RemoteAppModule::class, AppBindModule::class, NavigationModule::class])
class AppModule