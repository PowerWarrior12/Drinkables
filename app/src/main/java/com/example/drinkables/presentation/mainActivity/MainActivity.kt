package com.example.drinkables.presentation.mainActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.drinkables.R
import com.example.drinkables.presentation.DrinksApplication
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelFactory: MainActivityViewModel.MainActivityViewModelFactory

    private val viewModel: MainActivityViewModel by viewModels {
        viewModelFactory
    }

    private val navigator: Navigator =
        object : AppNavigator(this, R.id.container, supportFragmentManager) {
            override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
            ) {
                if (currentFragment != null)
                    fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
            }
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