package com.example.drinkables.presentation.drinksList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkables.R
import com.example.drinkables.databinding.DrinkItemFooterBinding

class DrinkLoadStateViewHolder(
    private val binding: DrinkItemFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): DrinkLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.drink_item_footer, parent, false)
            val binding = DrinkItemFooterBinding.bind(view)
            return DrinkLoadStateViewHolder(binding, retry)
        }
    }
}