package com.example.strength4mom.data.local.notes

import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getAllNotesStream(): Flow<List<Notes>>

    suspend fun insertNotes(notes: Notes)
}