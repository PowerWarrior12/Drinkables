package com.example.drinkables.presentation.MainActivity

import androidx.lifecycle.ViewModel
import com.example.drinkables.presentation.DrinksApplication
import com.example.drinkables.presentation.Screens

class MainActivityViewModel : ViewModel() {
    private val router = DrinksApplication.INSTANCE.router

    fun openDetailsFragment() {
        //TODO Create transaction to details window
    }

    fun openListFragment() {
        router.navigateTo(Screens.DrinksListFragmentInstance)
    }
}