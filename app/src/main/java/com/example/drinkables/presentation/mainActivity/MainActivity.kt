package com.example.drinkables.presentation.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.drinkables.R
import com.example.drinkables.databinding.ActivityMainBinding
import com.example.drinkables.presentation.DrinksApplication
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
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
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
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