package com.example.drinkables.presentation.drinksList

import androidx.recyclerview.widget.DiffUtil
import com.example.drinkables.domain.entities.DrinkViewEntity

object DrinkItemCallback : DiffUtil.ItemCallback<DrinkViewEntity>() {
    override fun areItemsTheSame(oldItem: DrinkViewEntity, newItem: DrinkViewEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DrinkViewEntity,
        newItem: DrinkViewEntity
    ): Boolean {
        return oldItem == newItem
    }
}