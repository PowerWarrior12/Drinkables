package com.example.drinkables.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.R
import com.example.drinkables.data.repositories.APP_PREFERENCES
import com.example.drinkables.data.repositories.DrinksRatingRemoteRepositoryImpl
import com.example.drinkables.data.repositories.USER_FIELD
import com.example.drinkables.databinding.ActivityMainBinding
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.domain.interactors.CreateOrUpdateUserInteractor
import com.example.drinkables.presentation.DrinksApplication
import com.example.drinkables.presentation.navigation.DialogNavigator
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelFactory: MainActivityViewModel.MainActivityViewModelFactory

    val binding by viewBinding(ActivityMainBinding::bind)

    private val viewModel: MainActivityViewModel by viewModels {
        viewModelFactory
    }

    private val navigator: Navigator =
        object : DialogNavigator(this, R.id.container, supportFragmentManager) {
            override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment,
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

    private fun initView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_catalog -> viewModel.onCatalogSelected()
                R.id.navigation_favourites -> viewModel.onFavouriteSelected()
            }
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        DrinksApplication.INSTANCE.appComponent.inject(this)
        //Open fragment if container is empty
        if (currentFragment == null) {
            viewModel.openFirstFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}