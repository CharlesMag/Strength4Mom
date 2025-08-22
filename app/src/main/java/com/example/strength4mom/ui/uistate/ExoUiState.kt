package com.example.strength4mom.ui.uistate

data class ExoUiState(
    val expanded: Boolean = false,
    val currentSet: Int = 0,
    val currentSetGhost: Int = 0,
    val exoDone: Boolean = false,
)