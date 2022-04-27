package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.databinding.DrinkPropertyItemBinding
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.utils.customAdapter.BaseViewHolder

class PropertyValueViewHolder(view: View) : BaseViewHolder<PropertyModel.PropertyValueModel>(view) {
    private val binding by viewBinding<DrinkPropertyItemBinding>()

    override fun bind(item: PropertyModel.PropertyValueModel, actionListener: ViewActionListener?) {
        binding.root.text = item.text
    }
}