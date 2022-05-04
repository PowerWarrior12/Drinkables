package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import com.example.drinkables.databinding.DrinkPropertyItemBinding
import com.example.drinkables.databinding.DrinkPropertyTitleItemBinding
import com.example.drinkables.domain.entities.PropertyModel
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun propertyAdapterDelegate() =
    adapterDelegateViewBinding<PropertyModel.PropertyValueModel, PropertyModel, DrinkPropertyItemBinding>(
        { layoutInflater, root -> DrinkPropertyItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.root.text = item.text
        }
    }

fun propertyTitleAdapterDelegate() =
    adapterDelegateViewBinding<PropertyModel.PropertyTitleModel, PropertyModel, DrinkPropertyTitleItemBinding>(
        { layoutInflater, root -> DrinkPropertyTitleItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.root.text = item.text
        }
    }