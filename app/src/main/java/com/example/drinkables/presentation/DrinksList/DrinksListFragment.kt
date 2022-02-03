package com.example.drinkables.presentation.DrinksList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkables.R
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

    private lateinit var drinksRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorButton: ImageButton
    private val drinksAdapter =
        DrinksAdapter.newInstance(object : DrinkViewHolder.OnHeartButtonClick {
            override fun run() {
                TODO("Create logic for click on heart button")
            }
        })

    override fun onAttach(context: Context) {
        DrinksApplication.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drinksViewModel.getDrinks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_drinks_list, container, false)
        initialization(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drinksViewModel.drinksList.observe(
            viewLifecycleOwner, { list ->
                drinksAdapter.submitList(list)
            }
        )

        drinksViewModel.loading.observe(
            viewLifecycleOwner, {
                when (it) {
                    true -> progressBar.visibility = View.VISIBLE
                    false -> progressBar.visibility = View.GONE
                }
            }
        )

        drinksViewModel.error.observe(
            viewLifecycleOwner, {
                when (it) {
                    true -> errorButton.visibility = View.VISIBLE
                    false -> errorButton.visibility = View.GONE
                }
            }
        )
    }

    private fun initialization(view: View) {
        drinksRecyclerView = view.findViewById(R.id.drinks_recycler_view)
        drinksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        drinksRecyclerView.adapter = drinksAdapter

        progressBar = view.findViewById(R.id.progress_bar)

        errorButton = view.findViewById(R.id.error_button)
        errorButton.setOnClickListener {
            drinksViewModel.getDrinks()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DrinksListFragment()
    }
}