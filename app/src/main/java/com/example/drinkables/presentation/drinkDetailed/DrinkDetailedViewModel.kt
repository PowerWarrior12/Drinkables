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
    private val id: Int
) : ViewModel() {

    val drinkDetailedLiveData = MutableLiveData<Drink>()

    init {
        getDrinkDetailed()
    }

    fun getDrinkDetailed() {
        viewModelScope.launch {
            val result = loadDrinkDetailedInteractor.run(id)
            when (result) {
                is Result.Error -> {
                    Log.d(TAG, result.exception.message ?: "")
                }
                is Result.Success -> {
                    drinkDetailedLiveData.postValue(result.data ?: Drink())
                }
            }
        }
    }

    class DrinkDetailedViewModelFactory @AssistedInject constructor(
        private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
        @Assisted private val id: Int
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DrinkDetailedViewModel(
                loadDrinkDetailedInteractor,
                id
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted id: Int): DrinkDetailedViewModel.DrinkDetailedViewModelFactory
        }
    }
}