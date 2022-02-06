package com.example.drinkables.presentation.drinksList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.domain.interactors.LoadDrinksInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class DrinksListViewModel(
    private val loadDrinksInteractor: LoadDrinksInteractor
) : ViewModel() {

    val drinksListLiveData = MutableLiveData<MutableList<DrinkViewEntity>>()
    val loadingLivaData = MutableLiveData<Boolean>(false)
    val errorLiveData = MutableLiveData<Boolean>(false)

    init {
        getDrinks()
    }

    fun getDrinks() {
        viewModelScope.launch {
            loadingLivaData.postValue(true)
            errorLiveData.postValue(false)
            when (val result = loadDrinksInteractor.run()) {
                is Result.Success -> {
                    drinksListLiveData.postValue(result.data ?: mutableListOf())
                }
                is Result.Error -> {
                    errorLiveData.postValue(true)
                }
            }
            loadingLivaData.postValue(false)
        }
    }

    class DrinksListViewModelFactory @Inject constructor(
        private val loadDrinksInteractor: LoadDrinksInteractor
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DrinksListViewModel(
                loadDrinksInteractor
            ) as T
        }
    }
}