package com.example.strength4mom.data.repository

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.strength4mom.data.dto.ExerciseResponse
import com.example.strength4mom.data.dto.Resource
import com.example.strength4mom.domain.ExerciseService
import retrofit2.HttpException
import java.io.IOException

class ExerciseRepositoryImpl(private val exerciseService: ExerciseService) : ExerciseRepository {

    override suspend fun getExerciseList(
        muscle: String?,
        name: String?,
        type: String?
    ): Resource<List<ExerciseResponse>> {
        return try {
            val response = exerciseService.fetchExercises(
                apiKey = "iTK9UnvYXz0/Cj77yQDNIQ==poYEvgXCSZYv0kyN",
                muscle = muscle?.takeIf { it.isNotBlank() },
                name = name?.takeIf { it.isNotBlank() },
                type = type?.takeIf { it.isNotBlank() }
            )
            if (response.isSuccessful) {
                Resource.Success(response.body() ?: emptyList())
            } else {
                Resource.Error("API ERROR ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Log.e("ExerciseRepo", "Network error", e)
            Resource.Error("Network error. Check your internet connection.")
        } catch (e: HttpException) {
            Log.e("ExerciseRepo", "HTTP error", e)
            Resource.Error("Unexpected server response.")
        } catch (e: Exception) {
            Log.e("ExerciseRepo", "Unexpected error", e)
            Resource.Error("Unknown error: ${e.localizedMessage}")
        }
    }

}