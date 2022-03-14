package com.example.drinkables.presentation.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.drinkables.R
import com.example.drinkables.presentation.DrinksApplication
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelFactory: MainActivityViewModel.MainActivityViewModelFactory

    private val navigator = AppNavigator(this, R.id.container, supportFragmentManager)
    private val viewModel: MainActivityViewModel by viewModels {
        viewModelFactory
    }

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DrinksApplication.INSTANCE.appComponent.inject(this)
        //Open fragment if container is empty
        if (currentFragment == null) {
            viewModel.openListFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}