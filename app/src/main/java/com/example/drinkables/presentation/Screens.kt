package com.example.drinkables.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.drinkables.presentation.DrinksList.DrinksListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    object DrinksListFragmentInstance : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return DrinksListFragment.newInstance()
        }
    }
}