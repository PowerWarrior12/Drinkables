package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.databinding.DrinkPropertyItemBinding
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.utils.customAdapter.BaseViewHolder

class PropertyTitleViewHolder(view: View) : BaseViewHolder<PropertyModel.PropertyTitleModel>(view) {

    private val binding by viewBinding<DrinkPropertyItemBinding>()

    override fun bind(item: PropertyModel.PropertyTitleModel, actionListener: ViewActionListener?) {
        binding.root.text = item.text
    }
}