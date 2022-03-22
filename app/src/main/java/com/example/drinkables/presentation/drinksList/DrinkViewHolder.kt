package com.example.drinkables.presentation.drinksList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.HEARD_BUTTON_SCALE_GROWTH
import com.example.drinkables.HEART_BUTTON_DURATION
import com.example.drinkables.R
import com.example.drinkables.databinding.DrinkItemBinding
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.utils.views.startJellyAnimation


class DrinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var drink: Drink? = null

    private val binding by viewBinding<DrinkItemBinding>()

    fun bind(drink: Drink, callback: DrinkViewListener) {
        binding.apply {
            this@DrinkViewHolder.drink = drink
            this@DrinkViewHolder.drink?.let { drink ->
                drinkTitleText.text = drink.title
                setHeartButtonBackground(drink)
                heartButton.setOnClickListener {
                    heartButton.startJellyAnimation(HEART_BUTTON_DURATION, HEARD_BUTTON_SCALE_GROWTH)
                    callback.onHeartButtonClick(drink.id)
                    drink.favourites = !drink.favourites
                    setHeartButtonBackground(drink)
                }
                root.setOnClickListener {
                    callback.onCurrentDrinkClick(drink.id)
                }
            }
        }
    }

    private fun setHeartButtonBackground(drink: Drink) {
        binding.heartButton.apply {
            if (drink.favourites) {
                setBackgroundResource(R.drawable.ic_heart_favourite)
            } else {
                setBackgroundResource(R.drawable.ic_heart)
            }
        }
    }

    interface DrinkViewListener {
        fun onHeartButtonClick(id: Int)
        fun onCurrentDrinkClick(id: Int)
    }
}