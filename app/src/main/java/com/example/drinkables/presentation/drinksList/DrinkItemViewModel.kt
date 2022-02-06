package com.example.drinkables.presentation.drinksList

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.drinkables.domain.entities.DrinkViewEntity

class DrinkItemViewModel : BaseObservable() {
    var drinkViewEntity: DrinkViewEntity? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get : Bindable
    val drinkTitle: String
        get() = drinkViewEntity?.title ?: ""
}