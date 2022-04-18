package com.example.drinkables.presentation.drinksFavorite

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.R
import com.example.drinkables.databinding.FragmentDrinksListBinding
import com.example.drinkables.presentation.DrinksApplication
import com.example.drinkables.presentation.mainActivity.MainActivity
import com.example.drinkables.utils.views.setState
import javax.inject.Inject

private const val FRAGMENT_ITEM_ID = R.id.navigation_favourites
private const val VISIBLE_NAVIGATION = true

class FavouritesListFragment : Fragment(R.layout.fragment_drinks_list) {
    @Inject
    lateinit var favouritesViewModelFactory: FavouritesViewModel.FavouritesViewModelFactory

    val favouritesViewModel by viewModels<FavouritesViewModel>() {
        favouritesViewModelFactory
    }

    private val favouritesAdapter =
        FavouritesAdapter(object : FavouriteDrinkViewHolder.FavouriteDrinkViewListener {
            override fun onCurrentDrinkClick(id: Int) {
                favouritesViewModel.onCurrentDrinkClick(id)
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
        (activity as MainActivity).binding.bottomNavigation.setState(
            FRAGMENT_ITEM_ID,
            VISIBLE_NAVIGATION
        )
        binding.apply {
            mainToolbar.toolbar.title = resources.getString(R.string.favourites_window)
            errorLayout.group.isVisible = false
            progressBar.isVisible = false
            drinksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            drinksRecyclerView.adapter = favouritesAdapter
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            drinksRecyclerView.addItemDecoration(decoration)
            mainToolbar.searchView.isVisible = false
        }
    }

    private fun observeData() {
        favouritesViewModel.favouriteDrinksLiveData.observe(
            viewLifecycleOwner
        ) { drinks ->
            favouritesAdapter.submitList(drinks.toMutableList())
        }
    }

    companion object {
        fun newInstance() = FavouritesListFragment()
    }
}