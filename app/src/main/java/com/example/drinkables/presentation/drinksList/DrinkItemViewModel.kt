package com.example.drinkables.presentation.drinksList

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.drinkables.domain.entities.Drink

class DrinkItemViewModel : BaseObservable() {
    var drink: Drink? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get : Bindable
    val drinkTitle: String
        get() = drink?.title ?: ""
}