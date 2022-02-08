package com.example.drinkables.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.lang.Exception
import java.net.URL

object ImageLoader {
    fun loadImageWithUrl(url: String): Bitmap {
        var bitmap: Bitmap? = null
        try {
            val input = URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap!!
    }
}