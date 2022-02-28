package com.example.drinkables.presentation.drinksList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.interactors.ChangeFavouriteDrinkInteractor
import com.example.drinkables.domain.interactors.LoadPagingDrinksInteractor
import com.example.drinkables.presentation.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DrinksListViewModel(
    private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
    private val loadPagingDrinksInteractor: LoadPagingDrinksInteractor,
    private val router: Router
) : ViewModel() {
    val loadingLivaData = MutableLiveData<Boolean>(false)
    val errorLiveData = MutableLiveData<Boolean>(false)
    val drinksFlow = getPagingDrinks()

    private fun getPagingDrinks(): Flow<PagingData<Drink>> {
        return loadPagingDrinksInteractor.run()
    }

    fun changeFavouriteDrink(drinkId: Int) {
        viewModelScope.launch {
            drinksFlow.collect { changeFavouriteDrinkInteractor.run(drinkId) }
        }
    }

    fun openDetailedWindow(id: Int) {
        router.navigateTo(Screens.drinkDetailedFragment(id))
    }

    class DrinksListViewModelFactory @Inject constructor(
        private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
        private val loadPagingDrinksInteractor: LoadPagingDrinksInteractor,
        private val router: Router
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