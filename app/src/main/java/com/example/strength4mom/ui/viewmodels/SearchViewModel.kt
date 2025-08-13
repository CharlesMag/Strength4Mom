package com.example.strength4mom.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strength4mom.data.dto.Exercise
import com.example.strength4mom.di.RetrofitInstance
import com.example.strength4mom.ui.uistate.SearchPageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    //Search Page UI state
    private val _uiState = MutableStateFlow(SearchPageUiState())
    val uiState: StateFlow<SearchPageUiState> = _uiState.asStateFlow()

    // State to store the exercises result
    var exercises = mutableStateOf<List<Exercise>>(emptyList())
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)
        private set

    fun fetchExercises(
        apiKey: String = "iTK9UnvYXz0/Cj77yQDNIQ==poYEvgXCSZYv0kyN",
        muscle: String? = null,
        name: String? = null,
        type: String? = null
    ) {
        val filteredMuscle = muscle?.takeIf { it.isNotBlank() }
        val filteredName = name?.takeIf { it.isNotBlank() }
        val filteredType = type?.takeIf { it.isNotBlank() }

        Log.d(
            "FetchExercises",
            "filteredMuscle: $filteredMuscle, filteredName: $filteredName, fitleredType: $filteredType"
        ) // <-- Add this

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitInstance.apiService.getExercises(
                    apiKey,
                    filteredMuscle,
                    filteredName,
                    filteredType
                )
                if (response.isSuccessful) {
                    exercises.value = response.body() ?: emptyList()
                    errorMessage.value = null
                } else {
                    errorMessage.value = "API Error: ${response.code()} ${response.message()}"
                    exercises.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("FetchExercises", "Failed to fetch exercises", e)
                exercises.value = emptyList() // If error occurs, set an empty list
                errorMessage.value = e.localizedMessage ?: "Unknown error occurred"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun muscleExpanded() {
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

    fun typeExpanded() {
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