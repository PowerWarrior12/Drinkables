package com.example.drinkables.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.drinkables.R
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {
    private val navigator = AppNavigator(this, R.id.container, supportFragmentManager)
    private val viewModel: MainActivityViewModel by viewModels()

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Open fragment if container is empty
        if (currentFragment == null) {
            viewModel.openListFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        DrinksApplication.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        DrinksApplication.INSTANCE.navigatorHolder.removeNavigator()
        super.onPause()
    }
}