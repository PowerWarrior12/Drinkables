package com.example.drinkables.presentation

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private val router = DrinksApplication.INSTANCE.router

    fun openDetailsFragment() {
        //TODO Create transaction to details window
    }

    fun openListFragment() {
        //TODO Create transaction to window a list of drinks
    }
}