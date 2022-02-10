package com.example.drinkables.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImageByUrl(url: String) {
    Glide
        .with(context)
        .load(url)
        .into(this)
}