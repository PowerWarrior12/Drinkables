package com.example.drinkables.presentation.DrinksList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.drinkables.R
import com.example.drinkables.domain.entities.DrinkViewEntity

class DrinksAdapter : ListAdapter<DrinkViewEntity, DrinkViewHolder>(DrinkItemCallback) {

    private var onHeartButtonClick: DrinkViewHolder.OnHeartButtonClick? = null

    object DrinkItemCallback : DiffUtil.ItemCallback<DrinkViewEntity>() {
        override fun areItemsTheSame(oldItem: DrinkViewEntity, newItem: DrinkViewEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DrinkViewEntity,
            newItem: DrinkViewEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.drink_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bind(currentList[position], this.onHeartButtonClick!!)
    }

    companion object {
        fun newInstance(
            onHeartButtonClick: DrinkViewHolder.OnHeartButtonClick
        ) = DrinksAdapter().apply {
            this.onHeartButtonClick = onHeartButtonClick
        }
    }
}