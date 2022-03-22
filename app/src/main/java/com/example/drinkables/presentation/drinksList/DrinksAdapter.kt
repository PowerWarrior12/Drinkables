package com.example.drinkables.presentation.drinksList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.drinkables.R
import com.example.drinkables.domain.entities.Drink

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

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavouriteDrink(drinkId: Int) {
        for (i in 0 until itemCount) {
            val drink = getItem(i)
            if (drink?.id == drinkId) {
                drink.favourites = !drink.favourites
            }
        }
        notifyDataSetChanged()
    }
}