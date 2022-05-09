package com.example.drinkables.presentation.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen

open class DialogScreen constructor(
    private val dialogCreator: Creator<FragmentFactory, DialogFragment>
) : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment {
        return dialogCreator.create(factory)
    }

    fun createDialog(factory: FragmentFactory): DialogFragment {
        return dialogCreator.create(factory)
    }
}