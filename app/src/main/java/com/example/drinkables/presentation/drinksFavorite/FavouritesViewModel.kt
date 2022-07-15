package com.example.drinkables.presentation.drinksFavorite

import androidx.lifecycle.*
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.interactors.LoadDrinksFavouriteInteractor
import com.example.drinkables.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesViewModel(
    private val loadDinkFavouritesInteractor: LoadDrinksFavouriteInteractor,
    private val router: Router,
) : ViewModel() {
    private val mutableFavouriteDrinksLiveData = MutableLiveData<List<Drink>>()
    val favouriteDrinksLiveData: LiveData<List<Drink>> = mutableFavouriteDrinksLiveData

    init {
        getFavouritesDrink()
    }

    private fun getFavouritesDrink() {
        viewModelScope.launch {
            loadDinkFavouritesInteractor.run().collect { drinks ->
                mutableFavouriteDrinksLiveData.postValue(drinks)
            }
        }
    }

    fun onCurrentDrinkClick(id: Int) {
        router.navigateTo(Screens.drinkDetailedFragment(id))
    }

    class FavouritesViewModelFactory @Inject constructor(
        private val loadDrinksFavouritesInteractor: LoadDrinksFavouriteInteractor,
        private val router: Router,
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavouritesViewModel(
                loadDrinksFavouritesInteractor,
                router
            ) as T
        }
    }
}