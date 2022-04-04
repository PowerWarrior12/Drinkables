package com.example.drinkables.presentation.drinksFavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.drinkables.R
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.presentation.drinksList.DrinkItemCallback

class FavouritesAdapter(private val favouriteDrinkCallback: FavouriteDrinkViewHolder.FavouriteDrinkViewListener) :
    ListAdapter<Drink, FavouriteDrinkViewHolder>(DrinkItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteDrinkViewHolder {
        return FavouriteDrinkViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favourite_drink_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavouriteDrinkViewHolder, position: Int) {
        holder.bind(getItem(position), favouriteDrinkCallback)
    }

    override fun submitList(list: MutableList<Drink>?) {
        super.submitList(list?.map {
            it.copy()
        })
    }
}