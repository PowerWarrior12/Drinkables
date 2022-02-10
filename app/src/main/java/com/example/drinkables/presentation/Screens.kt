package com.example.drinkables.presentation

import com.example.drinkables.presentation.drinkDetailed.DrinkDetailedFragment
import com.example.drinkables.presentation.drinksList.DrinksListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun drinksListFragment() = FragmentScreen {
        DrinksListFragment.newInstance()
    }

    fun drinkDetailedFragment(drinkId: Int) = FragmentScreen {
        DrinkDetailedFragment.newInstance(drinkId)
    }
}