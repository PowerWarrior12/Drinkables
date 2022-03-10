package com.example.drinkables.presentation.drinksList

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class DrinkStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<DrinkLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: DrinkLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DrinkLoadStateViewHolder {
        return DrinkLoadStateViewHolder.create(parent, retry)
    }
}