package com.example.drinkables.presentation.drinkDetailed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.HEARD_BUTTON_SCALE_GROWTH
import com.example.drinkables.HEART_BUTTON_DURATION
import com.example.drinkables.R
import com.example.drinkables.databinding.FragmentDrinkDetailedBinding
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.presentation.DrinksApplication
import com.example.drinkables.presentation.drinksList.DrinksListFragment
import com.example.drinkables.utils.setImageByUrl
import com.example.drinkables.utils.views.startJellyAnimation
import javax.inject.Inject

private const val DRINK_ID = "drinkId"
private val TAG = DrinkDetailedFragment::class.simpleName

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
                drinkViewModel.changeFavouriteDrink()
                setFragmentResult(
                    DrinksListFragment.RESULT_KEY,
                    bundleOf(DrinksListFragment.DRINK_ID to drinkId)
                )
                heartButton.startJellyAnimation(HEART_BUTTON_DURATION, HEARD_BUTTON_SCALE_GROWTH)
            }
            errorLayout.retryButton.setOnClickListener {
                drinkViewModel.reloadDrinkDetailed()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun observeData() {
        drinkViewModel.drinkDetailedLiveData.observe(viewLifecycleOwner, ::fillDrinkData)

        drinkViewModel.loadDrinkLiveData.observe(viewLifecycleOwner) { isLoading ->
            Log.d(TAG, "Changing the loading state")
            binding.apply {
                progressBar.isVisible = isLoading
                heartButton.isVisible = !isLoading
                motionContainer.getTransition(R.id.my_transition).isEnabled = !isLoading
            }
        }

        drinkViewModel.errorDrinkLiveData.observe(viewLifecycleOwner) { isError ->
            Log.d(TAG, "Changing the error state")
            binding.apply {
                errorLayout.group.isVisible = isError
                heartButton.isVisible = !isError
                motionContainer.getTransition(R.id.my_transition).isEnabled = !isError
            }
        }
    }

    private fun fillDrinkData(drink: Drink) {
        binding.apply {
            drinkTitleText.text = drink.title
            setHeartButtonBackground(drink)
            drinkDescriptionText.text = drink.description
            drink.imageUrl?.let {
                drinkImage.setImageByUrl(drink.imageUrl)
            }
        }
    }

    private fun setHeartButtonBackground(drink: Drink) {
        binding.heartButton.apply {
            when (drink.favourites) {
                true -> setBackgroundResource(R.drawable.ic_heart_favourite)
                false -> setBackgroundResource(R.drawable.ic_heart)
            }
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