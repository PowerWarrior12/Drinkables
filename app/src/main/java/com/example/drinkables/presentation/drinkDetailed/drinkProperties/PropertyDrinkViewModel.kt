package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drinkables.data.mappers.EntityMapper
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.entities.PropertyModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PropertyDrinkViewModel(
    private val drink: Drink,
    private val drinkToDrinkPropertyValuesMapper: EntityMapper<Drink, List<PropertyModel>>,
) : ViewModel() {

    val drinkLiveData: LiveData<List<PropertyModel>> =
        MutableLiveData(drinkToDrinkPropertyValuesMapper.mapEntity(drink))

    class PropertyDrinkViewModelFactory @AssistedInject constructor(
        @Assisted private val drink: Drink,
        private val drinkToDrinkPropertyValuesMapper: EntityMapper<Drink, List<PropertyModel>>,
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PropertyDrinkViewModel(drink, drinkToDrinkPropertyValuesMapper) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted drink: Drink): PropertyDrinkViewModelFactory
        }
    }
}