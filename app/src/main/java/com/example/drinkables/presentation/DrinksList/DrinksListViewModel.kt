package com.example.drinkables.presentation.DrinksList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.domain.interactors.LoadDrinksInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class DrinksListViewModel(
    private val loadDrinksInteractor: LoadDrinksInteractor
) : ViewModel() {

    val drinksList = MutableLiveData<MutableList<DrinkViewEntity>>()
    val loading = MutableLiveData<Boolean>(false)
    val error = MutableLiveData<Boolean>(false)

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.IO)

    fun getDrinks() {
        scope.launch {
            loading.postValue(true)
            error.postValue(false)
            when (val result = loadDrinksInteractor.run()) {
                is Result.Success -> {
                    drinksList.postValue(result.data!!)
                }
                is Result.Error -> {
                    drinksList.postValue(mutableListOf())
                    error.postValue(true)
                }
            }
            loading.postValue(false)
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
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