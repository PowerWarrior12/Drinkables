package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import androidx.recyclerview.widget.DiffUtil
import com.example.drinkables.domain.entities.PropertyModel

object PropertyModelDiffCallback : DiffUtil.ItemCallback<PropertyModel>() {
    override fun areItemsTheSame(oldItem: PropertyModel, newItem: PropertyModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: PropertyModel, newItem: PropertyModel) =
        oldItem == newItem
}