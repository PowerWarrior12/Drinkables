package com.example.drinkables.presentation.drinkDetailed

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.HEARD_BUTTON_SCALE_GROWTH
import com.example.drinkables.HEART_BUTTON_DURATION
import com.example.drinkables.R
import com.example.drinkables.databinding.FragmentDrinkDetailedBinding
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.presentation.DrinksApplication
import com.example.drinkables.presentation.drinkDetailed.drinkProperties.PropertyTitleAdapter
import com.example.drinkables.presentation.drinkDetailed.drinkProperties.PropertyValueAdapter
import com.example.drinkables.presentation.drinksList.DrinksListFragment
import com.example.drinkables.presentation.mainActivity.MainActivity
import com.example.drinkables.utils.customAdapter.CompositeDelegateAdapter
import com.example.drinkables.utils.setImageByUrl
import com.example.drinkables.utils.views.ViewSlideDirection
import com.example.drinkables.utils.views.setVisibility
import com.example.drinkables.utils.views.slideHorizontal
import com.example.drinkables.utils.views.startJellyAnimation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

private const val DRINK_ID = "drinkId"
private val TAG = DrinkDetailedFragment::class.simpleName
private const val VISIBLE_NAVIGATION = false

/**
 * Use the [DrinkDetailedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrinkDetailedFragment : Fragment(R.layout.fragment_drink_detailed) {

    private val drinkId: Int
        get() = checkNotNull(arguments?.getInt(DRINK_ID))

    private val binding: FragmentDrinkDetailedBinding by viewBinding(FragmentDrinkDetailedBinding::bind)

    private val drinkViewModel by viewModels<DrinkDetailedViewModel>() {
        drinkDetailedViewModelFactory.create(drinkId)
    }

    private val propertiesSheetBehaviour by lazy {
        BottomSheetBehavior.from(binding.bottomSheetProperties.root)
    }

    private val propertiesAdapter = CompositeDelegateAdapter.Builder<PropertyModel>(object :
        DiffUtil.ItemCallback<PropertyModel>() {
        override fun areItemsTheSame(oldItem: PropertyModel, newItem: PropertyModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: PropertyModel, newItem: PropertyModel) =
            oldItem == newItem
    })
        .addAdapter(PropertyValueAdapter())
        .addAdapter(PropertyTitleAdapter())
        .build()

    @Inject
    lateinit var drinkDetailedViewModelFactory: DrinkDetailedViewModel.DrinkDetailedViewModelFactory.Factory

    override fun onAttach(context: Context) {
        DrinksApplication.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun initViews() {
        (activity as MainActivity).binding.bottomNavigation.setVisibility(VISIBLE_NAVIGATION)
        initToolbar()
        initDrinkDetailed()
        initBottomSheet()
    }

    private fun initToolbar() {
        binding.toolbarDetailed.apply {
            heartButton.setOnClickListener {
                drinkViewModel.changeFavouriteDrink()
                setFragmentResult(
                    DrinksListFragment.RESULT_KEY,
                    bundleOf(DrinksListFragment.DRINK_ID to drinkId,
                        DrinksListFragment.IS_FAVOURITE_CHANGED to drinkViewModel.isFavouriteChanged)
                )
                heartButton.startJellyAnimation(
                    HEART_BUTTON_DURATION,
                    HEARD_BUTTON_SCALE_GROWTH
                )
            }
            backButton.setOnClickListener {
                drinkViewModel.openBackView()
            }
        }
    }

    private fun initDrinkDetailed() {
        binding.drinkDetailedContent.apply {
            errorLayout.retryButton.setOnClickListener {
                drinkViewModel.reloadDrinkDetailed()
            }
            propertiesButton?.setOnClickListener {
                drinkViewModel.onPropertiesButtonClick()
            }
        }
    }

    private fun initBottomSheet() {
        binding.bottomSheetProperties.apply {
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> root.isVisible = false
                else -> {
                    propertiesRecyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = propertiesAdapter
                        val decoration =
                            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                        addItemDecoration(decoration)
                    }
                    propertiesSheetBehaviour.addBottomSheetCallback(object :
                        BottomSheetBehavior.BottomSheetCallback() {
                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {
                            labelProperties.slideHorizontal(0, ViewSlideDirection.Left, slideOffset)
                        }
                    })
                }
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
                drinkDetailedContent.progressBar.isVisible = isLoading
                toolbarDetailed.heartButton.isVisible = !isLoading
                drinkDetailedContent.motionContainer.getTransition(R.id.my_transition)?.isEnabled =
                    !isLoading
            }
        }

        drinkViewModel.errorDrinkLiveData.observe(viewLifecycleOwner) { isError ->
            Log.d(TAG, "Changing the error state")
            binding.apply {
                drinkDetailedContent.errorLayout.group.isVisible = isError
                toolbarDetailed.heartButton.isVisible = !isError
                drinkDetailedContent.motionContainer.getTransition(R.id.my_transition)?.isEnabled =
                    !isError
            }
        }

        drinkViewModel.drinkPropertiesLiveData.observe(
            viewLifecycleOwner,
            propertiesAdapter::submitList
        )
    }

    private fun fillDrinkData(drink: Drink) {
        binding.apply {
            setHeartButtonBackground(drink)
            drink.apply {
                drinkDetailedContent.apply {
                    drinkDescriptionText.text = description
                    toolbarDetailed.toolbar.title = title
                    imageUrl?.let {
                        drinkImage.setImageByUrl(imageUrl)
                    }
                }
            }
        }
    }

    private fun setHeartButtonBackground(drink: Drink) {
        binding.toolbarDetailed.heartButton.apply {
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