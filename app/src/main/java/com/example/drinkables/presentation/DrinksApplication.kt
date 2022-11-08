package com.example.drinkables.presentation

import android.app.Application
import com.example.drinkables.data.repositories.DrinksRatingRemoteRepositoryImpl
import com.example.drinkables.presentation.di.AppComponent
import com.example.drinkables.presentation.di.DaggerAppComponent
import com.google.firebase.FirebaseApp

class DrinksApplication : Application() {

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            "AppComponent isn't initialized"
        }

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