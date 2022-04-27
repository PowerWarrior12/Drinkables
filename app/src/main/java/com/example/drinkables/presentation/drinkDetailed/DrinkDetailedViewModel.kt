package com.example.drinkables.presentation.drinkDetailed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.domain.common.Result
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.domain.interactors.ChangeFavouriteDrinkInteractor
import com.example.drinkables.domain.interactors.LoadDrinkDetailedInteractor
import com.example.drinkables.domain.interactors.UpdateDrinkFavouriteInteractor
import com.example.drinkables.presentation.navigation.DialogRouter
import com.example.drinkables.presentation.navigation.Screens
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

private val TAG = DrinkDetailedViewModel::class.simpleName

class DrinkDetailedViewModel(
    private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
    private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
    private val updateDrinkFavouriteInteractor: UpdateDrinkFavouriteInteractor,
    private val drinkToDrinkPropertyValuesMapper: EntityMapper<Drink, List<PropertyModel>>,
    private val router: DialogRouter,
    private val drinkId: Int,
) : ViewModel() {

    val drinkDetailedLiveData = MutableLiveData<Drink>()
    val drinkPropertiesLiveData = MutableLiveData<List<PropertyModel>>()
    val loadDrinkLiveData = MutableLiveData<Boolean>(false)
    val errorDrinkLiveData = MutableLiveData<Boolean>(false)
    var isFavouriteChanged: Boolean = false

    init {
        getDrinkDetailed()
    }

    fun getDrinkDetailed() {
        viewModelScope.launch {
            loadDrinkLiveData.postValue(true)
            val result = loadDrinkDetailedInteractor.run(drinkId)
            loadDrinkLiveData.postValue(false)
            when (result) {
                is Result.Error -> {
                    Log.d(TAG, result.exception.message ?: "")
                    errorDrinkLiveData.postValue(true)
                }
                is Result.Success -> {
                    result.data.let { drink ->
                        val updateDrink = updateDrinkFavouriteInteractor.run(drink)
                        drinkDetailedLiveData.postValue(updateDrink)
                        drinkPropertiesLiveData.postValue(drinkToDrinkPropertyValuesMapper.mapEntity(
                            drink))
                    }
                }
            }
        }
    }

    fun reloadDrinkDetailed() {
        errorDrinkLiveData.postValue(false)
    }

    fun openBackView() {
        router.exit()
    }

    fun changeFavouriteDrink() {
        isFavouriteChanged = !isFavouriteChanged
        viewModelScope.launch {
            drinkDetailedLiveData.value?.let { drink ->
                drinkDetailedLiveData.postValue(changeFavouriteDrinkInteractor.run(drink))
            }
        }
    }

    fun onPropertiesButtonClick() {
        router.showDialog(Screens.propertyDrinkDialogFragment(drinkDetailedLiveData.value
            ?: Drink()))
    }

    class DrinkDetailedViewModelFactory @AssistedInject constructor(
        private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
        private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
        private val updateDrinkFavouriteInteractor: UpdateDrinkFavouriteInteractor,
        private val drinkToDrinkPropertyValuesMapper: EntityMapper<Drink, List<PropertyModel>>,
        private val router: DialogRouter,
        @Assisted private val drinkId: Int,
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DrinkDetailedViewModel(
                loadDrinkDetailedInteractor,
                changeFavouriteDrinkInteractor,
                updateDrinkFavouriteInteractor,
                drinkToDrinkPropertyValuesMapper,
                router,
                drinkId
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted drinkId: Int): DrinkDetailedViewModelFactory
        }
    }
}