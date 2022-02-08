package com.example.drinkables.presentation.drinksList

import androidx.recyclerview.widget.RecyclerView
import com.example.drinkables.databinding.DrinkItemBinding
import com.example.drinkables.domain.entities.Drink

private const val DEFAULT_DRINK_ID = 0

class DrinkViewHolder(private val binding: DrinkItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewModel = DrinkItemViewModel()
    }

    fun bind(drinkViewEntity: Drink, callback: DrinkViewListener) {
        binding.apply {
            viewModel?.drink = drinkViewEntity
            heartButton.setOnClickListener {
                callback.onHeartButtonClick(
                    binding.viewModel?.drink?.id ?: DEFAULT_DRINK_ID
                )
            }
            root.setOnClickListener {
                callback.onCurrentDrinkClick(
                    binding.viewModel?.drink?.id ?: DEFAULT_DRINK_ID
                )
            }
        }
    }

    interface DrinkViewListener {
        fun onHeartButtonClick(id: Int)
        fun onCurrentDrinkClick(id: Int)
    }
}