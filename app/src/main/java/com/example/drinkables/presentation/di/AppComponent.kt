package com.example.drinkables.presentation.di

import com.example.drinkables.presentation.drinkDetailed.DrinkDetailedFragment
import com.example.drinkables.presentation.drinksList.DrinksListFragment
import com.example.drinkables.presentation.mainActivity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(fragment: DrinksListFragment)
    fun inject(activity: MainActivity)
    fun inject(fragment: DrinkDetailedFragment)
}