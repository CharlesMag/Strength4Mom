package com.example.strength4mom.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.strength4mom.ui.uistate.NotesScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(NotesScreenUiState())
    val uiState: StateFlow<NotesScreenUiState> = _uiState.asStateFlow()

    fun notesTextFieldInput(inputText: String) {
        _uiState.update { it.copy(notesBarTextField = inputText) }
    }
}

