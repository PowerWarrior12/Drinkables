package com.example.drinkables.presentation.drinksList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.interactors.ChangeFavouriteDrinkInteractor
import com.example.drinkables.domain.interactors.LoadPagingDrinksInteractor
import com.example.drinkables.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DrinksListViewModel(
    private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
    private val loadPagingDrinksInteractor: LoadPagingDrinksInteractor,
    private val router: Router,
) : ViewModel() {
    var drinksFlow: Flow<PagingData<Drink>>
        private set

    init {
        drinksFlow = getPagingDrinks()
            .cachedIn(viewModelScope)
    }

    private fun getPagingDrinks(): Flow<PagingData<Drink>> {
        return loadPagingDrinksInteractor.run()
    }

    fun changeFavouriteDrink(drink: Drink) {
        viewModelScope.launch {
            changeFavouriteDrinkInteractor.run(drink)
        }
    }

    fun updateDrinksFlowByName(name: String) {
        drinksFlow = loadPagingDrinksInteractor.run(name)
            .cachedIn(viewModelScope)
    }

    fun openDetailedWindow(id: Int) {
        router.navigateTo(Screens.drinkDetailedFragment(id))
    }

    class DrinksListViewModelFactory @Inject constructor(
        private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
        private val loadPagingDrinksInteractor: LoadPagingDrinksInteractor,
        private val router: Router,
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DrinksListViewModel(
                changeFavouriteDrinkInteractor,
                loadPagingDrinksInteractor,
                router
            ) as T
        }
    }
}