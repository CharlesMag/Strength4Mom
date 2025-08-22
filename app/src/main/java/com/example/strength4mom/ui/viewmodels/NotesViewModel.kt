package com.example.strength4mom.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strength4mom.data.local.notes.Notes
import com.example.strength4mom.data.local.notes.NotesRepository
import com.example.strength4mom.ui.uistate.NotesScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(NotesScreenUiState())
    val uiState: StateFlow<NotesScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            notesRepository.getAllNotesStream()
                .collect { notes -> _uiState.update { it.copy(notesList = notes) } }
        }
    }

    suspend fun saveNotes() {
        notesRepository.insertNotes(
            Notes(
                notes = _uiState.value.notesBarTextField.orEmpty()
            )
        )
    }

    fun notesBarEntryChange(inputText: String) {
        _uiState.update { it.copy(notesBarTextField = inputText) }
        if (uiState.value.notesBarTextField?.isNotEmpty() == true ) {
            _uiState.update { it.copy(isEntryValid = true) }
        } else {
            _uiState.update { it.copy(isEntryValid = false) }
        }
    }

    fun clearInputTextField() {
        _uiState.update { it.copy(notesBarTextField = null) }
        if (uiState.value.notesBarTextField?.isNotEmpty() == true ) {
            _uiState.update { it.copy(isEntryValid = true) }
        } else {
            _uiState.update { it.copy(isEntryValid = false) }
        }
    }
}

