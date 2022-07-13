package com.example.drinkables.presentation.navigation

import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.presentation.ErrorDialogFragment
import com.example.drinkables.presentation.drinkDetailed.DrinkDetailedFragment
import com.example.drinkables.presentation.drinkDetailed.drinkProperties.PropertyDrinkDialogFragment
import com.example.drinkables.presentation.drinksFavorite.FavouritesListFragment
import com.example.drinkables.presentation.drinksList.DrinksListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun drinksListFragment() = FragmentScreen {
        DrinksListFragment.newInstance()
    }

    fun drinkDetailedFragment(drinkId: Int) = FragmentScreen {
        DrinkDetailedFragment.newInstance(drinkId)
    }

    fun favouritesListFragment() = FragmentScreen {
        FavouritesListFragment.newInstance()
    }

    fun propertyDrinkDialogFragment(drink: Drink) = DialogScreen {
        PropertyDrinkDialogFragment.newInstance(drink)
    }

    fun errorDialogFragment(message: String) = DialogScreen {
        ErrorDialogFragment(message)
    }
}