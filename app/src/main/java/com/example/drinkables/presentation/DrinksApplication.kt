package com.example.drinkables.presentation

import android.app.Application
import com.github.terrakok.cicerone.Cicerone

class DrinksApplication : Application() {

    private val cicerone = Cicerone.create()

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            "AppComponent isn't initialized"
        }

    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.create()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: DrinksApplication
            private set
    }
}