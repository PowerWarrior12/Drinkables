package com.example.drinkables.presentation.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drinkables.domain.interactors.UserRatingsLoadInteractor
import com.example.drinkables.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel(
    private val router: Router,
    private val userRatingsLoadInteractor: UserRatingsLoadInteractor
) : ViewModel() {

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRatingsLoadInteractor.run()
            }
        }
    }

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
        private val router: Router,
        private val userRatingsLoadInteractor: UserRatingsLoadInteractor
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(router, userRatingsLoadInteractor) as T
        }
    }
}