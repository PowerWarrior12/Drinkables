package com.example.drinkables.presentation.drinksList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.interactors.ChangeFavouriteDrinkInteractor
import com.example.drinkables.domain.interactors.LoadDrinksInteractor
import com.example.drinkables.domain.interactors.UpdateDrinksFavouritesInteractor
import com.example.drinkables.presentation.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.drinkables.domain.common.Result as Result

class DrinksListViewModel(
    private val loadDrinksInteractor: LoadDrinksInteractor,
    private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
    private val updateDrinksFavouritesInteractor: UpdateDrinksFavouritesInteractor,
    private val router: Router
) : ViewModel() {
    val drinksListLiveData = MutableLiveData<MutableList<Drink>>()
    val favouriteDrinksLiveData = MutableLiveData<MutableList<Drink>>()
    val loadingLivaData = MutableLiveData<Boolean>(false)
    val errorLiveData = MutableLiveData<Boolean>(false)

    init {
        getDrinks()
    }

    fun getDrinks() {
        viewModelScope.launch {
            loadingLivaData.postValue(true)
            errorLiveData.postValue(false)
            // variable for getting the result of a data request for a list of drinks
            val result = loadDrinksInteractor.run()
            when (result) {
                is Result.Success -> {
                    result.data.let { drinks ->
                        drinksListLiveData.postValue(updateDrinksFavouritesInteractor.run(drinks))
                    }
                }
                is Result.Error -> {
                    errorLiveData.postValue(true)
                }
            }
            loadingLivaData.postValue(false)
        }
    }

    fun updateDrinksFavourites() {
        viewModelScope.launch {
            drinksListLiveData.value?.let { drinks ->
                drinksListLiveData.postValue(updateDrinksFavouritesInteractor.run(drinks))
            }
        }
    }

    fun changeFavouriteDrink(drinkId: Int) {
        viewModelScope.launch {
            drinksListLiveData.value?.let { drinks ->
                drinksListLiveData.postValue(changeFavouriteDrinkInteractor.run(drinkId, drinks))
            }
        }
    }

    fun openDetailedWindow(id: Int) {
        router.navigateTo(Screens.drinkDetailedFragment(id))
    }

    class DrinksListViewModelFactory @Inject constructor(
        private val loadDrinksInteractor: LoadDrinksInteractor,
        private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
        private val updateDrinksFavouritesInteractor: UpdateDrinksFavouritesInteractor,
        private val router: Router
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DrinksListViewModel(
                loadDrinksInteractor,
                changeFavouriteDrinkInteractor,
                updateDrinksFavouritesInteractor,
                router
            ) as T
        }
    }
}