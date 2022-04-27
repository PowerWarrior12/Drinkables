package com.example.drinkables.presentation.navigation

import com.github.terrakok.cicerone.Router

class DialogRouter: Router() {
    fun showDialog(screen: DialogScreen) = executeCommands(ShowDialog(screen))
    fun closeDialog(screen: DialogScreen) = executeCommands(CloseDialog(screen))
}