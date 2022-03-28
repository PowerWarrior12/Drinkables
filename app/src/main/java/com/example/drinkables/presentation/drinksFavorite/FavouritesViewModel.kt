package com.example.drinkables.presentation.drinksFavorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.interactors.LoadDrinksFavouriteInteractor
import com.example.drinkables.presentation.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesViewModel(
    loadDinkFavouritesInteractor: LoadDrinksFavouriteInteractor,
    private val router: Router
) : ViewModel() {
    val favouriteDrinksLiveData = MutableLiveData<List<Drink>>()

    init {
        viewModelScope.launch {
            favouriteDrinksLiveData.postValue(loadDinkFavouritesInteractor.run())
        }
    }

    fun openDetailedWindow(id: Int) {
        router.navigateTo(Screens.drinkDetailedFragment(id))
    }

    class FavouritesViewModelFactory @Inject constructor(
        private val loadDrinksFavouritesInteractor: LoadDrinksFavouriteInteractor,
        private val router: Router
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavouritesViewModel(
                loadDrinksFavouritesInteractor,
                router
            ) as T
        }
    }
}