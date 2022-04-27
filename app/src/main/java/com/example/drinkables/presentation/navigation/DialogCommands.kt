package com.example.drinkables.presentation.navigation

import com.github.terrakok.cicerone.Command

data class ShowDialog(val screen: DialogScreen): Command
data class CloseDialog(val screen: DialogScreen): Command