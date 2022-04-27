package com.example.drinkables.presentation.drinkDetailed.drinkProperties

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkables.R
import com.example.drinkables.domain.entities.PropertyModel
import com.example.drinkables.utils.customAdapter.BaseDelegateAdapter

class PropertyTitleAdapter : BaseDelegateAdapter<PropertyTitleViewHolder, PropertyModel.PropertyTitleModel>() {
    override fun getLayoutId(): Int {
        return R.layout.drink_property_title_item
    }

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return PropertyTitleViewHolder(view)
    }

    override fun <A> isForViewType(items: MutableList<A>, position: Int): Boolean {
        return items[position] is PropertyModel.PropertyTitleModel
    }
}