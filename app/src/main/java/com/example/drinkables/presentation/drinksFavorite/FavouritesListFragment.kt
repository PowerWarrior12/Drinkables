package com.example.drinkables.presentation.drinksFavorite

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.databinding.FragmentDrinksListBinding
import com.example.drinkables.presentation.DrinksApplication
import javax.inject.Inject

class FavouritesListFragment : Fragment() {
    @Inject
    lateinit var favouritesViewModelFactory: FavouritesViewModel.FavouritesViewModelFactory

    val favouritesViewModel by viewModels<FavouritesViewModel>() {
        favouritesViewModelFactory
    }

    private val favouritesAdapter =
        FavouritesAdapter(object : FavouriteDrinkViewHolder.FavouriteDrinkViewListener {
            override fun onCurrentDrinkClick(id: Int) {
                favouritesViewModel.openDetailedWindow(id)
            }
        })

    private val binding by viewBinding(FragmentDrinksListBinding::bind)

    override fun onAttach(context: Context) {
        DrinksApplication.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        binding.apply {
            drinksRecyclerView.adapter = favouritesAdapter
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            drinksRecyclerView.addItemDecoration(decoration)
        }
    }

    private fun observeData() {
        favouritesViewModel.favouriteDrinksLiveData.observe(
            viewLifecycleOwner,
            favouritesAdapter::submitList
        )
    }

    companion object {
        fun newInstance() = FavouritesListFragment()
    }
}