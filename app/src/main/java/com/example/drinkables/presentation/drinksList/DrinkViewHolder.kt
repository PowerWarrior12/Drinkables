package com.example.drinkables.presentation.drinksList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.R
import com.example.drinkables.databinding.DrinkItemBinding
import com.example.drinkables.domain.entities.Drink

class DrinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var drink: Drink? = null

    private val binding by viewBinding<DrinkItemBinding>()

    fun bind(drinkViewEntity: Drink, callback: DrinkViewListener) {
        binding.apply {
            drink = drinkViewEntity
            drink?.let { drink ->
                drinkTitleText.text = drink.title
                setHeartButtonBackground(drink)
                heartButton.apply {
                    setOnClickListener {
                        callback.onHeartButtonClick(drink.id)
                    }
                }
                root.setOnClickListener {
                    callback.onCurrentDrinkClick(drink.id)
                }
            }
        }
    }

    private fun setHeartButtonBackground(drink : Drink){
        binding.heartButton.apply {
            when (drink.favourites) {
                true -> setBackgroundResource(R.drawable.ic_heart_favourite)
                false -> setBackgroundResource(R.drawable.ic_heart)
            }
        }
    }

    interface DrinkViewListener {
        fun onHeartButtonClick(id: Int)
        fun onCurrentDrinkClick(id: Int)
    }
}