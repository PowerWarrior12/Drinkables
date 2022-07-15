package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import com.example.drinkables.databinding.DrinkPropertyIndicatorItemBinding
import com.example.drinkables.databinding.DrinkPropertyItemBinding
import com.example.drinkables.databinding.DrinkPropertyRatingItemBinding
import com.example.drinkables.databinding.DrinkPropertyTitleItemBinding
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.utils.views.customViews.RatingView
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

fun propertyRatingAdapterDelegate(callback: RatingView.OnItemClickListener) =
    adapterDelegateViewBinding<PropertyModel.PropertyRatingModel, PropertyModel, DrinkPropertyRatingItemBinding>(
        { layoutInflater, root -> DrinkPropertyRatingItemBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.addOnItemClickListener(callback)
        bind {
            binding.root.value = item.value
        }
    }

fun propertyIndicatorAdapterDelegate() =
    adapterDelegateViewBinding<PropertyModel.PropertyValueIndicatorModel, PropertyModel, DrinkPropertyIndicatorItemBinding>(
        { layoutInflater, root -> DrinkPropertyIndicatorItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.root.value = item.value
            binding.root.maxValue = item.maxValue
        }
    }