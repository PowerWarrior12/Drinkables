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
import dagger.assisted.Assisted

private val TAG = DrinkDetailedViewModel::class.simpleName

class DrinkDetailedViewModel(
    private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
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
                    result.data.let(drinkDetailedLiveData::postValue)
                }
            }
        }
    }

    class DrinkDetailedViewModelFactory @AssistedInject constructor(
        private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
        @Assisted private val drinkId: Int
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DrinkDetailedViewModel(
                loadDrinkDetailedInteractor,
                drinkId
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted drinkId: Int): DrinkDetailedViewModel.DrinkDetailedViewModelFactory
        }
    }
}