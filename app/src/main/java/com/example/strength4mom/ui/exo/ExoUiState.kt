package com.example.strength4mom.ui.exo

data class ExoUiState(
    val expanded: Boolean = false,
    val currentSet: Int = 0,
    val currentSetGhost: Int = 0,
    val exoDone: Boolean = false,
)