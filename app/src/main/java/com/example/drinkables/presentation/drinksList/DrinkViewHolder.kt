package com.example.drinkables.presentation.drinksList

import androidx.recyclerview.widget.RecyclerView
import com.example.drinkables.databinding.DrinkItemBinding
import com.example.drinkables.domain.entities.DrinkViewEntity

class DrinkViewHolder(private val binding: DrinkItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewModel = DrinkItemViewModel()
    }

    fun bind(drinkViewEntity: DrinkViewEntity, callback: DrinkViewListener) {
        binding.apply {
            viewModel?.drinkViewEntity = drinkViewEntity
            heartButton.setOnClickListener {
                callback.onHeartButtonClick()
            }
        }
    }

    interface DrinkViewListener {
        fun onHeartButtonClick()
    }
}