package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.utils.views.customViews.RatingView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class PropertyAdapter(onRatingClick: RatingView.OnItemClickListener) : AsyncListDifferDelegationAdapter<PropertyModel>(
    PropertyModelDiffCallback,
    propertyAdapterDelegate(),
    propertyTitleAdapterDelegate(),
    propertyRatingAdapterDelegate(onRatingClick),
    propertyIndicatorAdapterDelegate()
)