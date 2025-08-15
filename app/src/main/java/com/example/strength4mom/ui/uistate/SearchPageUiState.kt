package com.example.strength4mom.ui.uistate

import com.example.strength4mom.data.dto.ExerciseResponse

data class SearchPageUiState(
    val exercises: List<ExerciseResponse> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    var searchBarQuery: String? = null,
    var muscleDropdownSelection: String? = null,
    var typeDropdownSelection: String? = null,
    var muscleExpanded: Boolean = false,
    var typeExpanded: Boolean = false,
)