package com.example.drinkables.presentation.drinksList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkables.databinding.FragmentDrinksListBinding
import com.example.drinkables.presentation.DrinksApplication
import javax.inject.Inject

/**
 * Use the [DrinksListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrinksListFragment : Fragment() {

    @Inject
    lateinit var drinksListViewModelFactory: DrinksListViewModel.DrinksListViewModelFactory

    private val drinksViewModel: DrinksListViewModel by viewModels {
        drinksListViewModelFactory
    }

    private val drinksAdapter = DrinksAdapter(object : DrinkViewHolder.DrinkViewListener {
        override fun onHeartButtonClick() {
            //TODO Create logic for click on heart button
        }
    })
    private val binding by lazy {
        FragmentDrinksListBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        DrinksApplication.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        observeData()
    }

    private fun initViews() {
        binding.drinksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.drinksRecyclerView.adapter = drinksAdapter

        binding.errorButton.setOnClickListener {
            drinksViewModel.getDrinks()
        }
    }

    private fun observeData() {
        drinksViewModel.drinksListLiveData.observe(
            viewLifecycleOwner, { list ->
                drinksAdapter.submitList(list)
            }
        )

        drinksViewModel.loadingLivaData.observe(
            viewLifecycleOwner, { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        )

        drinksViewModel.errorLiveData.observe(
            viewLifecycleOwner, { isError ->
                binding.errorButton.isVisible = isError
            }
        )
    }

    companion object {
        fun newInstance() = DrinksListFragment()
    }
}