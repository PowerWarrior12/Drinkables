package com.example.drinkables.utils.customAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface IDelegateAdapter<out VH : RecyclerView.ViewHolder, out T> {
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: @UnsafeVariance VH, items: List<@UnsafeVariance T>, position: Int)

    fun onRecycled(holder: @UnsafeVariance VH)

    fun <A> isForViewType(items: MutableList<A>, position: Int): Boolean
}