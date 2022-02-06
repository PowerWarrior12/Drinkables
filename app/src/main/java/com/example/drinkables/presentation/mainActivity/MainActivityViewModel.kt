package com.example.drinkables.presentation.mainActivity

import androidx.lifecycle.ViewModel
import com.example.drinkables.presentation.DrinksApplication
import com.example.drinkables.presentation.Screens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {
    @Inject
    lateinit var router: Router

    init {
        DrinksApplication.INSTANCE.appComponent.inject(this)
    }

    fun openDetailsFragment() {
        //TODO Create transaction to details window
    }

    fun openListFragment() {
        router.navigateTo(Screens.drinksListFragment())
    }
}