package com.example.drinkables.presentation.drinksList

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.R
import com.example.drinkables.databinding.FragmentDrinksListBinding
import com.example.drinkables.presentation.DrinksApplication
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Use the [DrinksListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrinksListFragment : Fragment(R.layout.fragment_drinks_list) {

    @Inject
    lateinit var drinksListViewModelFactory: DrinksListViewModel.DrinksListViewModelFactory

    private val drinksViewModel: DrinksListViewModel by viewModels {
        drinksListViewModelFactory
    }

    private val drinksAdapter = DrinksAdapter(object : DrinkViewHolder.DrinkViewListener {
        override fun onHeartButtonClick(id: Int) {
            drinksViewModel.changeFavouriteDrink(id)
        }

        override fun onCurrentDrinkClick(id: Int) {
            drinksViewModel.openDetailedWindow(id)
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
        binding.drinksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.drinksRecyclerView.adapter = drinksAdapter.withLoadStateHeaderAndFooter(
            header = DrinkStateAdapter { drinksAdapter.retry() },
            footer = DrinkStateAdapter { drinksAdapter.retry() }
        )
        binding.errorButton.setOnClickListener { drinksAdapter.retry() }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            drinksViewModel.drinksFlow.collectLatest(drinksAdapter::submitData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            drinksAdapter.loadStateFlow.collect { loadState ->
                //Show loading bar
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                //Show error button
                binding.errorButton.isVisible = loadState.source.refresh is LoadState.Error
            }
        }
    }

    companion object {
        fun newInstance() = DrinksListFragment()
    }
}