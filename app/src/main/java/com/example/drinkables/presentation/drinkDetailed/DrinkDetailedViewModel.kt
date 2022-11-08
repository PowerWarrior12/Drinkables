package com.example.drinkables.presentation.drinkDetailed

import android.util.Log
import androidx.lifecycle.*
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.data.repositories.DrinksRatingRemoteRepository
import com.example.drinkables.data.repositories.DrinksRatingRepository
import com.example.drinkables.data.repositories.UserLocalRepository
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
    private val userLocalRepository: UserLocalRepository,
    private val drinksRatingRemoteRepository: DrinksRatingRemoteRepository,
    private val drinksRatingRepository: DrinksRatingRepository,
    private val router: DialogRouter,
    private val drinkId: Int,
) : ViewModel() {

    private val mutableDrinkDetailedLiveData = MutableLiveData<Drink>()
    private val mutableDrinkPropertiesLiveData = MutableLiveData<MutableList<PropertyModel>>()
    private val mutableLoadDrinkLiveData = MutableLiveData<Boolean>(false)
    private val mutableFatalErrorDrinkLiveData = MutableLiveData<Boolean>(false)
    private val mutableUserNameLiveData = MutableLiveData<String?>(null)

    val drinkDetailedLiveData: LiveData<Drink> = mutableDrinkDetailedLiveData
    val drinkPropertiesLiveData: LiveData<MutableList<PropertyModel>> = mutableDrinkPropertiesLiveData
    val loadDrinkLiveData: LiveData<Boolean> = mutableLoadDrinkLiveData
    val fatalErrorDrinkLiveData: LiveData<Boolean> = mutableFatalErrorDrinkLiveData
    val userNameLiveData: LiveData<String?> = mutableUserNameLiveData

    var isFavouriteChanged: Boolean = false
        private set

    init {
        viewModelScope.launch {
            mutableUserNameLiveData.postValue(userLocalRepository.getUserName())
        }
        getDrinkDetailed()
    }

    private fun getDrinkDetailed() {
        viewModelScope.launch {
            mutableLoadDrinkLiveData.postValue(true)
            val result = loadDrinkDetailedInteractor.run(drinkId)
            mutableLoadDrinkLiveData.postValue(false)
            when (result) {
                is Result.Error -> {
                    Log.d(TAG, result.exception.message ?: "")
                    mutableFatalErrorDrinkLiveData.postValue(true)
                }
                is Result.Success -> {
                    result.data.let { drink ->
                        val updateDrink = updateDrinkFavouriteInteractor.run(drink)
                        mutableDrinkDetailedLiveData.postValue(updateDrink)
                        loadDrinksProperties(drink)
                    }
                }
            }
        }
    }

    fun reloadDrinkDetailed() {
        mutableFatalErrorDrinkLiveData.postValue(false)
    }

    fun openBackView() {
        router.exit()
    }

    fun changeFavouriteDrink() {
        isFavouriteChanged = !isFavouriteChanged
        viewModelScope.launch {
            drinkDetailedLiveData.value?.let { drink ->
                mutableDrinkDetailedLiveData.postValue(changeFavouriteDrinkInteractor.run(drink))
            }
        }
    }

    fun onPropertiesButtonClick() {
        mutableDrinkDetailedLiveData.value?.let { drink ->
            router.showDialog(Screens.propertyDrinkDialogFragment(drink))
        }
    }

    fun onRatingChanged(rating: Int) {
        viewModelScope.launch {
            val ratingProperty =
                drinkPropertiesLiveData.value?.find { property ->
                    property is PropertyModel.PropertyRatingModel
                } as PropertyModel.PropertyRatingModel?
            ratingProperty?.let {
                val ratingPropertyIndex = drinkPropertiesLiveData.value?.indexOf(ratingProperty)
                ratingPropertyIndex?.let {
                    drinkPropertiesLiveData.value?.set(ratingPropertyIndex, ratingProperty.copy(value = rating))
                    val ratingPropertyResult =
                        drinkPropertiesLiveData.value?.get(it) as PropertyModel.PropertyRatingModel
                    addOrUpdateRatingInteractor.run(ratingPropertyResult)
                    addOrUpdateRatingRemote(ratingPropertyResult)
                }
            }
        }
    }

    private fun addOrUpdateRatingRemote(ratingProperty: PropertyModel.PropertyRatingModel) {
        viewModelScope.launch {
            userNameLiveData.value?.let {
                drinksRatingRemoteRepository.addOrUpdateRating(ratingProperty, it)
            }
        }
    }

    private suspend fun loadDrinksProperties(drink: Drink) {
        val properties = drinkToDrinkPropertyValuesMapper.mapEntity(drink).toMutableList()
        val drinkRating = PropertyModel.PropertyRatingModel(drink.id, DEFAULT_RATING)
        properties.add(drinkRating)
        mutableDrinkPropertiesLiveData.postValue(properties)
        loadUsersRatings(drink.id)
        loadDrinksRating(drink)
    }

    private suspend fun loadUsersRatings(drinkId: Int) {
        val result = drinksRatingRepository.loadAllRatingsByDrink(drinkId)
        if (result is Result.Success) {
            drinkPropertiesLiveData.value?.addAll(result.data)
        } else if (result is Result.Error){
            val errorMessage = result.exception.message.toString()
            router.showDialog(Screens.errorDialogFragment(errorMessage))
            Log.d(TAG, errorMessage)
        }
    }

    private suspend fun loadDrinksRating(drink: Drink) {
        loadDrinkRatingInteractor.run(drink.id).collect { resultRating ->
            if (resultRating is Result.Success) {
                val ratingPropertyIndex =
                    drinkPropertiesLiveData.value?.indexOf(drinkPropertiesLiveData.value?.find { property ->
                        property is PropertyModel.PropertyRatingModel
                    })
                ratingPropertyIndex?.let { index ->
                    drinkPropertiesLiveData.value?.set(index, resultRating.data)
                }
            } else if (resultRating is Result.Error) {
                val errorMessage = resultRating.exception.message.toString()
                router.showDialog(Screens.errorDialogFragment(errorMessage))
                Log.d(TAG, errorMessage)
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
        private val userLocalRepository: UserLocalRepository,
        private val drinksRatingRemoteRepository: DrinksRatingRemoteRepository,
        private val drinksRatingRepository: DrinksRatingRepository,
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
                userLocalRepository,
                drinksRatingRemoteRepository,
                drinksRatingRepository,
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