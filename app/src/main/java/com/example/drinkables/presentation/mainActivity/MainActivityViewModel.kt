package com.example.drinkables.presentation.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drinkables.presentation.Screens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityViewModel(
    private val router: Router
) : ViewModel() {

    fun openFirstFragment() {
        router.newRootScreen(Screens.drinksListFragment())
    }

    fun onCatalogSelected() {
        router.navigateTo(Screens.drinksListFragment())
    }

    fun onFavouriteSelected() {
        router.navigateTo(Screens.favouritesListFragment())
    }

    class MainActivityViewModelFactory @Inject constructor(
        private val router: Router
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(router) as T
        }
    }
}