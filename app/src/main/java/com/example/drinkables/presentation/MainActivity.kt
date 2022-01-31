package com.example.drinkables.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.drinkables.R
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import java.lang.RuntimeException
import java.util.*

private const val LIST_FRAGMENT = 1
private const val DETAILS_FRAGMENT = 2

class MainActivity : AppCompatActivity() {
    private val navigator = AppNavigator(this, R.id.frame_layout, supportFragmentManager)
    private val viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        viewModel.navigationHolder.setNavigator(navigator)
        if (supportFragmentManager.findFragmentById(R.id.frame_layout) == null){
            viewModel.openListFragment()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.navigationHolder.removeNavigator()
    }
}