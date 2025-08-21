package com.example.strength4mom.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strength4mom.data.dto.Resource
import com.example.strength4mom.data.repository.ExerciseRepository
import com.example.strength4mom.ui.uistate.SearchPageUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    //Search Page UI state
    private val _uiState = MutableStateFlow(SearchPageUiState())
    val uiState: StateFlow<SearchPageUiState> = _uiState.asStateFlow()

    fun loadExerciseList(
        muscle: String? = null,
        name: String? = null,
        type: String? = null
    ) {

        //Since a network call is being done, Koin is proving the Dispatcher.IO for it to run on this thread and avoid a UI freeze by using the default UI dispatcher
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = exerciseRepository.getExerciseList(muscle, name, type)
            when (result) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(

                            exercises = emptyList(),
                            isLoading = false,
                            errorMessage = result.message ?: "unknown error"
                        )
                    }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            exercises = result.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    fun muscleDropdownExpanded() {
        if (uiState.value.muscleExpanded) {
            _uiState.update { currentState ->
                currentState.copy(
                    muscleExpanded = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    muscleExpanded = true
                )
            }
        }
    }

    fun typeDropdownExpanded() {
        if (uiState.value.typeExpanded) {
            _uiState.update { currentState ->
                currentState.copy(
                    typeExpanded = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    typeExpanded = true
                )
            }
        }
    }

    fun searchBarQueryChange(inputText: String) {
        _uiState.update { it.copy(searchBarQuery = inputText) }
    }

    fun muscleDropdownSelection(selection: String) {
        _uiState.update { it.copy(muscleDropdownSelection = selection) }
    }

    fun typeDropdownSelection(selection: String) {
        _uiState.update { it.copy(typeDropdownSelection = selection) }
    }
}