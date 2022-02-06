package com.example.drinkables.presentation

import com.example.drinkables.presentation.drinksList.DrinksListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun drinksListFragment() = FragmentScreen {
        DrinksListFragment.newInstance()
    }
}