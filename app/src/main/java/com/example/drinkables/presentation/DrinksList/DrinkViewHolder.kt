package com.example.drinkables.presentation.DrinksList

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkables.R
import com.example.drinkables.domain.entities.DrinkViewEntity

class DrinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var drinkViewEntity: DrinkViewEntity

    private val heartButton by lazy<ImageButton> {
        itemView.findViewById(R.id.heart_button)
    }

    private val titleText by lazy<TextView> {
        itemView.findViewById(R.id.drink_title_text)
    }

    fun bind(drinkViewEntity: DrinkViewEntity, callback: OnHeartButtonClick) {
        this.drinkViewEntity = drinkViewEntity
        titleText.text = this.drinkViewEntity.title

        this.heartButton.setOnClickListener {
            callback.run()
        }
    }

    interface OnHeartButtonClick {
        fun run()
    }
}