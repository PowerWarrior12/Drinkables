package com.example.drinkables.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.drinkables.R

fun ImageView.setImageByUrl(url: String) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.ic_image_placeholder)
        .into(this)
}