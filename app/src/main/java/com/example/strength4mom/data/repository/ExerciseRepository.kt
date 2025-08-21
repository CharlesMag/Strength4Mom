package com.example.strength4mom.data.repository

import com.example.strength4mom.data.dto.ExerciseResponse
import com.example.strength4mom.data.dto.Resource

interface ExerciseRepository {

    suspend fun getExerciseList(
        muscle: String?,
        name: String?,
        type: String?
    ): Resource<List<ExerciseResponse>>

}