package com.example.drinkables.presentation.drinkDetailed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drinkables.domain.interactors.LoadDrinkDetailedInteractor
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.interactors.ChangeFavouriteDrinkInteractor
import com.example.drinkables.domain.interactors.UpdateDrinkFavouriteInteractor
import dagger.assisted.Assisted

private val TAG = DrinkDetailedViewModel::class.simpleName

class DrinkDetailedViewModel(
    private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
    private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
    private val updateDrinkFavouriteInteractor: UpdateDrinkFavouriteInteractor,
    private val drinkId: Int
) : ViewModel() {

    val drinkDetailedLiveData = MutableLiveData<Drink>()

    init {
        getDrinkDetailed()
    }

    fun getDrinkDetailed() {
        viewModelScope.launch {
            val result = loadDrinkDetailedInteractor.run(drinkId)
            when (result) {
                is Result.Error -> {
                    Log.d(TAG, result.exception.message ?: "")
                }
                is Result.Success -> {
                    result.data.let { drink ->
                        val updateDrink = updateDrinkFavouriteInteractor.run(drink)
                        drinkDetailedLiveData.postValue(updateDrink)
                    }
                }
            }
        }
    }

    fun changeFavouriteDrink() {
        viewModelScope.launch {
            drinkDetailedLiveData.value?.let { drink ->
                drinkDetailedLiveData.postValue(changeFavouriteDrinkInteractor.run(drink))
            }
        }
    }

    class DrinkDetailedViewModelFactory @AssistedInject constructor(
        private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
        private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
        private val updateDrinkFavouriteInteractor: UpdateDrinkFavouriteInteractor,
        @Assisted private val drinkId: Int
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DrinkDetailedViewModel(
                loadDrinkDetailedInteractor,
                changeFavouriteDrinkInteractor,
                updateDrinkFavouriteInteractor,
                drinkId
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted drinkId: Int): DrinkDetailedViewModel.DrinkDetailedViewModelFactory
        }
    }
}