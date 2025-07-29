package com.example.strength4mom.ui.theme.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.strength4mom.network.Exercise
import com.example.strength4mom.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {


    // State to store the exercises result
    var exercises = mutableStateOf<List<Exercise>>(emptyList())
    var errorMessage = mutableStateOf<String?>(null)
        var isLoading = mutableStateOf(false)
        private set

    // Handle the API request
    fun fetchExercises(
        apiKey: String = "iTK9UnvYXz0/Cj77yQDNIQ==poYEvgXCSZYv0kyN",
        muscle: String?,
        name: String?,
        type: String?
    ) {
        val filteredMuscle = muscle?.takeIf { it.isNotBlank() }
        val filteredName = name?.takeIf { it.isNotBlank() }
        val filteredType = type?.takeIf { it.isNotBlank() }

        Log.d("FetchExercises", "filteredMuscle: $filteredMuscle, filteredName: $filteredName, fitleredType: $filteredType") // <-- Add this

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitInstance.apiService.getExercises(apiKey, filteredMuscle, filteredName, filteredType)
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
}
