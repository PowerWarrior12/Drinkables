package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.R
import com.example.drinkables.databinding.PropertyDrinkDialogFragmentBinding
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.presentation.DrinksApplication
import com.example.drinkables.utils.customAdapter.CompositeDelegateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

const val DRINK_KEY = "drink_key"

class PropertyDrinkDialogFragment : BottomSheetDialogFragment() {

    private val drink: Drink
        get() = checkNotNull(arguments?.getSerializable(DRINK_KEY) as Drink)

    private val binding: PropertyDrinkDialogFragmentBinding by viewBinding()

    @Inject
    lateinit var propertyViewModelFactory: PropertyDrinkViewModel.PropertyDrinkViewModelFactory.Factory

    private val propertyDrinkViewModel by viewModels<PropertyDrinkViewModel> {
        propertyViewModelFactory.create(drink)
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

    override fun onAttach(context: Context) {
        DrinksApplication.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.property_drink_dialog_fragment, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        binding.propertiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = propertiesAdapter
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
    }

    private fun observeData() {
        propertyDrinkViewModel.drinkLiveData.observe(
            viewLifecycleOwner,
            propertiesAdapter::submitList
        )
    }

    companion object {
        fun newInstance(drink: Drink) = PropertyDrinkDialogFragment().apply {
            arguments = bundleOf(DRINK_KEY to drink)
        }
    }
}