package com.example.drinkables.presentation.drinksList

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.R
import com.example.drinkables.databinding.FragmentDrinksListBinding
import com.example.drinkables.presentation.DrinksApplication
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        binding.drinksRecyclerView.adapter = drinksAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            drinksViewModel.drinksFlow.collect { drinks ->
                drinksAdapter.submitData(drinks)
            }
        }

        drinksViewModel.loadingLivaData.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        drinksViewModel.errorLiveData.observe(viewLifecycleOwner) { isError ->
            binding.errorButton.isVisible = isError
        }
    }

    companion object {
        fun newInstance() = DrinksListFragment()
    }
}