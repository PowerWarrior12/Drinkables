package com.example.drinkables.utils.customAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T, actionListener: ViewActionListener? = null)
    interface ViewActionListener
}