package com.example.drinkables.presentation.drinkDetailed

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drinkables.domain.interactors.LoadDrinkDetailedInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.utils.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val DRINK_ID = "drinkId"
private val TAG = DrinkDetailedViewModel::class.simpleName

class DrinkDetailedViewModel(
    private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
    private val id: Int
) : ViewModel() {

    val drinkDetailedLiveData = MutableLiveData<DrinkViewEntity>()
    val imageLiveData = MutableLiveData<Bitmap>()

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
                    drinkDetailedLiveData.postValue(result.data ?: DrinkViewEntity())
                    getImage(result.data.imageUrl)
                }
            }
        }
    }

    private suspend fun getImage(url: String) {
        withContext(Dispatchers.IO) {
            val bitmap = ImageLoader.loadImageWithUrl(url)
            imageLiveData.postValue(bitmap)
        }
    }

    class DrinkDetailedViewModelFactory @AssistedInject constructor(
        private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
        @Assisted(DRINK_ID) private val id: Int
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
            fun create(@Assisted(DRINK_ID) id: Int): DrinkDetailedViewModel.DrinkDetailedViewModelFactory
        }
    }
}