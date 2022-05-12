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
import com.example.drinkables.domain.interactors.*
import com.example.drinkables.presentation.navigation.DialogRouter
import com.example.drinkables.presentation.navigation.Screens
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private val TAG = DrinkDetailedViewModel::class.simpleName
private const val DEFAULT_RATING = 0

class DrinkDetailedViewModel(
    private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
    private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
    private val updateDrinkFavouriteInteractor: UpdateDrinkFavouriteInteractor,
    private val loadDrinkRatingInteractor: LoadDrinkRatingInteractor,
    private val addOrUpdateRatingInteractor: AddOrUpdateRatingInteractor,
    private val drinkToDrinkPropertyValuesMapper: EntityMapper<Drink, List<PropertyModel>>,
    private val router: DialogRouter,
    private val drinkId: Int,
) : ViewModel() {

    val drinkDetailedLiveData = MutableLiveData<Drink>()
    val drinkPropertiesLiveData = MutableLiveData<MutableList<PropertyModel>>()
    val loadDrinkLiveData = MutableLiveData<Boolean>(false)
    val errorDrinkLiveData = MutableLiveData<Boolean>(false)
    var isFavouriteChanged: Boolean = false

    init {
        getDrinkDetailed()
    }

    private fun getDrinkDetailed() {
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
                        loadDrinksProperties(drink)
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
        drinkDetailedLiveData.value?.let { drink ->
            router.showDialog(Screens.propertyDrinkDialogFragment(drink))
        }
    }

    fun onRatingChanged(rating: Int) {
        viewModelScope.launch {
            val ratingProperty =
                drinkPropertiesLiveData.value?.find { it is PropertyModel.PropertyRatingModel } as PropertyModel.PropertyRatingModel?
            ratingProperty?.let {
                val ratingPropertyIndex = drinkPropertiesLiveData.value?.indexOf(ratingProperty)
                ratingPropertyIndex?.let {
                    drinkPropertiesLiveData.value?.set(ratingPropertyIndex, ratingProperty.copy(value = rating))
                    addOrUpdateRatingInteractor.run(drinkPropertiesLiveData.value?.get(it) as PropertyModel.PropertyRatingModel)
                }
            }
        }
    }

    private suspend fun loadDrinksProperties(drink: Drink) {
        val properties = drinkToDrinkPropertyValuesMapper.mapEntity(drink).toMutableList()
        val drinkRating = PropertyModel.PropertyRatingModel(drink.id, DEFAULT_RATING)
        properties.add(drinkRating)
        drinkPropertiesLiveData.postValue(properties)
        loadDrinksRating(drink)
    }

    private suspend fun loadDrinksRating(drink: Drink) {
        loadDrinkRatingInteractor.run(drink.id).collect { resultRating ->
            if (resultRating is Result.Success) {
                val ratingPropertyIndex =
                    drinkPropertiesLiveData.value?.indexOf(drinkPropertiesLiveData.value?.find { it is PropertyModel.PropertyRatingModel })
                ratingPropertyIndex?.let { drinkPropertiesLiveData.value?.set(it, resultRating.data) }
            } else if (resultRating is Result.Error) {
                Log.d(TAG, resultRating.exception.message.toString())
            }
        }
    }

    class DrinkDetailedViewModelFactory @AssistedInject constructor(
        private val loadDrinkDetailedInteractor: LoadDrinkDetailedInteractor,
        private val changeFavouriteDrinkInteractor: ChangeFavouriteDrinkInteractor,
        private val updateDrinkFavouriteInteractor: UpdateDrinkFavouriteInteractor,
        private val drinkToDrinkPropertyValuesMapper: EntityMapper<Drink, List<PropertyModel>>,
        private val loadDrinkRatingInteractor: LoadDrinkRatingInteractor,
        private val addOrUpdateRatingInteractor: AddOrUpdateRatingInteractor,
        private val router: DialogRouter,
        @Assisted private val drinkId: Int,
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DrinkDetailedViewModel(
                loadDrinkDetailedInteractor,
                changeFavouriteDrinkInteractor,
                updateDrinkFavouriteInteractor,
                loadDrinkRatingInteractor,
                addOrUpdateRatingInteractor,
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