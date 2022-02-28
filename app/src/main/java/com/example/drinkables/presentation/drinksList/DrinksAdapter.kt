package com.example.drinkables.presentation.drinksList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import com.example.drinkables.R
import com.example.drinkables.domain.entities.Drink
import kotlinx.coroutines.flow.Flow

class DrinksAdapter(private val drinkViewListener: DrinkViewHolder.DrinkViewListener) :
    PagingDataAdapter<Drink, DrinkViewHolder>(DrinkItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.drink_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bind(getItem(position) ?: Drink(), this.drinkViewListener)
    }
}