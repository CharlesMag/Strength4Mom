package com.example.strength4mom.ui.uistate

data class SearchPageUiState(
    var searchBarQuery: String? = null,
    var muscleDropdownSelection: String? = null,
    var typeDropdownSelection: String? = null,
    var muscleExpanded: Boolean = false,
    var typeExpanded: Boolean = false,
)