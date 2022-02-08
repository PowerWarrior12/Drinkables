package com.example.drinkables.presentation.drinksList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.drinkables.R
import com.example.drinkables.domain.entities.Drink

class DrinksAdapter(private val drinkViewListener: DrinkViewHolder.DrinkViewListener) :
    ListAdapter<Drink, DrinkViewHolder>(DrinkItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.drink_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bind(currentList[position], this.drinkViewListener)
    }
}