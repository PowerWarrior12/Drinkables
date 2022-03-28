package com.example.drinkables.presentation.drinksFavorite

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.databinding.FavouriteDrinkItemBinding
import com.example.drinkables.domain.entities.Drink

class FavouriteDrinkViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var drink: Drink? = null
    private val binding by viewBinding<FavouriteDrinkItemBinding>()

    fun bind(drink: Drink, callback: FavouriteDrinkViewListener){
        binding.apply {
            this@FavouriteDrinkViewHolder.drink = drink
            this@FavouriteDrinkViewHolder.drink?.let { drink ->
                drinkTitleText.text = drink.title
                root.setOnClickListener{
                    callback.onCurrentDrinkClick(drink.id)
                }
            }

        }
    }

    interface FavouriteDrinkViewListener{
        fun onCurrentDrinkClick(id: Int)
    }
}