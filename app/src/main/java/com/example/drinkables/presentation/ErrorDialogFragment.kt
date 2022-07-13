package com.example.drinkables.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.drinkables.R

class ErrorDialogFragment(private val message: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { fragment ->
            val builder = AlertDialog.Builder(fragment)
            builder.setTitle(R.string.error_text)
                .setTitle(message)
                .setPositiveButton(R.string.positive_button) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: super.onCreateDialog(savedInstanceState)
    }
}