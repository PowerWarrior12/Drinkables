package com.example.drinkables.presentation.di

import dagger.Module

@Module(includes = [RemoteAppModule::class, AppBindModule::class, ScreenAppModule::class])
class AppModule