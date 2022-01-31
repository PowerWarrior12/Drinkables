package com.example.drinkables.presentation

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Cicerone

class MainActivityViewModel : ViewModel() {
    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder get() = cicerone.getNavigatorHolder()

    fun openDetailsFragment(){

    }

    fun openListFragment(){

    }
}