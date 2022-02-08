package com.example.drinkables.presentation.drinksList

import androidx.recyclerview.widget.DiffUtil
import com.example.drinkables.domain.entities.Drink

object DrinkItemCallback : DiffUtil.ItemCallback<Drink>() {
    override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Drink,
        newItem: Drink
    ): Boolean {
        return oldItem == newItem
    }
}