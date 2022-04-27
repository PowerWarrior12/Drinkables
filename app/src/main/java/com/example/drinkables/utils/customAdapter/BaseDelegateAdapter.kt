package com.example.drinkables.utils.customAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseDelegateAdapter<VH : ViewHolder, T>(private val actionListener: BaseViewHolder.ViewActionListener? = null) :
    IDelegateAdapter<VH, T> {
    protected abstract fun getLayoutId(): Int
    protected abstract fun createViewHolder(view: View): ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return createViewHolder(
            LayoutInflater.from(parent.context).inflate(getLayoutId(), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, items: List<T>, position: Int) {
        (holder as BaseViewHolder<T>).bind(items[position], actionListener)
    }

    override fun onRecycled(holder: VH) {
    }
}