package com.example.drinkables.utils.views

import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setState(fragmentId: Int, visible: Boolean) {
    setItemSelected(fragmentId)
    setVisibility(visible)
}

fun BottomNavigationView.setItemSelected(fragmentId: Int) {
    var i = 0
    while (i < menu.size()) {
        val item: MenuItem = menu.getItem(i)
        if (item.itemId == fragmentId) {
            item.isChecked = true
            break
        }
        i++
    }
}

fun BottomNavigationView.setVisibility(visible: Boolean) {
    visibility = when (visible) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}