package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkables.R
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.utils.customAdapter.BaseDelegateAdapter

class PropertyValueAdapter : BaseDelegateAdapter<PropertyValueViewHolder, PropertyModel.PropertyValueModel>() {
    override fun getLayoutId(): Int {
        return R.layout.drink_property_item
    }

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return PropertyValueViewHolder(view)
    }

    override fun <A> isForViewType(items: MutableList<A>, position: Int): Boolean {
        return items[position] is PropertyModel.PropertyValueModel
    }
}