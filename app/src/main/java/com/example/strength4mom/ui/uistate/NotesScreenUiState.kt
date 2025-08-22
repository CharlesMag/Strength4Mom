package com.example.strength4mom.ui.uistate

import com.example.strength4mom.data.local.notes.Notes

data class NotesScreenUiState(
    val notesList: List<Notes> = listOf(),
    val notesBarTextField: String? = null,
    val isEntryValid: Boolean = false
)