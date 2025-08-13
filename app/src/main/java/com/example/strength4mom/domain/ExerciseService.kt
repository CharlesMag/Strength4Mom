package com.example.strength4mom.domain

import com.example.strength4mom.data.dto.Exercise
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ExerciseService {
    @GET("exercises")
    suspend fun fetchExercises(
        @Header("X-Api-Key") apiKey: String,
        @Query("muscle") muscle: String? = null,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null
    ): Response<List<Exercise>>
}