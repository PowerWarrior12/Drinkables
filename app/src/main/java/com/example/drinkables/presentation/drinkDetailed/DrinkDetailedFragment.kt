package com.example.drinkables.presentation.drinkDetailed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.drinkables.databinding.FragmentDrinkDetailedBinding
import com.example.drinkables.presentation.DrinksApplication
import javax.inject.Inject

private const val DRINK_ID = "drinkId"

/**
 * Use the [DrinkDetailedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrinkDetailedFragment : Fragment() {

    private val drinkId: Int
        get() = checkNotNull(arguments?.getInt(DRINK_ID))

    private val binding by lazy {
        FragmentDrinkDetailedBinding.inflate(layoutInflater)
    }
    private val drinkViewModel by viewModels<DrinkDetailedViewModel>() {
        drinkDetailedViewModelFactory.create(drinkId)
    }

    @Inject
    lateinit var drinkDetailedViewModelFactory: DrinkDetailedViewModel.DrinkDetailedViewModelFactory.Factory

    override fun onAttach(context: Context) {
        DrinksApplication.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

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
        drinkViewModel.drinkDetailedLiveData.observe(
            viewLifecycleOwner, { entity ->
                binding.apply {
                    drinkTitleText.text = entity.title
                    drinkDescriptionText.text = entity.description
                }
            }
        )
        drinkViewModel.imageLiveData.observe(
            viewLifecycleOwner, { bitmap ->
                binding.drinkImage.setImageBitmap(bitmap)
            }
        )
    }

    companion object {
        fun newInstance(id: Int): DrinkDetailedFragment {
            return DrinkDetailedFragment().apply {
                arguments = Bundle().apply {
                    putInt(DRINK_ID, id)
                }
            }
        }
    }
}