package com.example.strength4mom.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.strength4mom.ui.uistate.ExoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.absoluteValue

class ExoViewModel : ViewModel() {
    //Exo UI state
    private val _uiState = MutableStateFlow(ExoUiState())
    val uiState: StateFlow<ExoUiState> = _uiState.asStateFlow()

    fun updateExpanded() {
        if (uiState.value.expanded) {
            _uiState.update { currentState ->
                currentState.copy(
                    expanded = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    expanded = true
                )
            }
        }
    }

    fun triageCurrentSet(exoSets: Int) {
        val exoSetsMax = exoSets - 1
        when (uiState.value.currentSet) {
            in 0..exoSetsMax -> {
                updateCurrentSet(exoSets)
            }

            exoSets.absoluteValue -> {
                updateCurrentSetGhost()
                _uiState.update { currentState ->
                    currentState.copy(
                        exoDone = true
                    )
                }
            }

            else -> {
                updateCurrentSetGhost()
            }
        }
    }

    private fun updateCurrentSetGhost() {
        _uiState.update { currentState ->
            currentState.copy(
                currentSetGhost = currentState.currentSetGhost.inc(),
            )
        }
    }

    private fun updateCurrentSet(exoSets: Int) {
        if (uiState.value.currentSet < exoSets) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentSet = currentState.currentSet.inc(),
                    currentSetGhost = currentState.currentSetGhost.inc()
                )
            }
        }

        if (uiState.value.currentSet == exoSets) {
            _uiState.update { currentState ->
                currentState.copy(
                    exoDone = true,
                )
            }
        }
    }

    fun closeOpenDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                currentSetGhost = currentState.currentSetGhost.dec(),
            )
        }
    }

    fun resetCurrentSet() {
        _uiState.update { currentState ->
            currentState.copy(
                currentSet = 0,
                currentSetGhost = 0,
            )
        }
    }

    fun updateExoCapsule() {
        if (!uiState.value.exoDone) {
            _uiState.update { currentState ->
                currentState.copy(
                    exoDone = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    exoDone = false
                )
            }
        }
    }
}