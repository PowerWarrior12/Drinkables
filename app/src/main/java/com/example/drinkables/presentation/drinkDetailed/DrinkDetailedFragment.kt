package com.example.drinkables.presentation.drinkDetailed

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.R
import com.example.drinkables.databinding.FragmentDrinkDetailedBinding
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.presentation.DrinksApplication
import javax.inject.Inject
import com.example.drinkables.utils.setImageByUrl

private const val DRINK_ID = "drinkId"

/**
 * Use the [DrinkDetailedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrinkDetailedFragment : Fragment(R.layout.fragment_drink_detailed) {

    private val drinkId: Int
        get() = checkNotNull(arguments?.getInt(DRINK_ID))

    private val binding by viewBinding(FragmentDrinkDetailedBinding::bind)

    private val drinkViewModel by viewModels<DrinkDetailedViewModel>() {
        drinkDetailedViewModelFactory.create(drinkId)
    }

    @Inject
    lateinit var drinkDetailedViewModelFactory: DrinkDetailedViewModel.DrinkDetailedViewModelFactory.Factory

    override fun onAttach(context: Context) {
        DrinksApplication.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun initViews() {
        binding.apply {
            heartButton.setOnClickListener {
                //TODO Implement logic on button click
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        drinkViewModel.drinkDetailedLiveData.observe(viewLifecycleOwner, ::fillDrinkData)
    }

    private fun fillDrinkData(drink: Drink) {
        binding.apply {
            drinkTitleText.text = drink.title
            drinkDescriptionText.text = drink.description
            drinkImage.setImageByUrl(drink.imageUrl)
        }
    }

    companion object {
        fun newInstance(drinkId: Int): DrinkDetailedFragment {
            return DrinkDetailedFragment().apply {
                arguments = bundleOf(DRINK_ID to drinkId)
            }
        }
    }
}