package com.example.strength4mom.data.local.notes

import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notesDao: NotesDao) : NotesRepository {
    override suspend fun insertNotes(notes: Notes) = notesDao.insertNotes(notes)

    override fun getAllNotesStream(): Flow<List<Notes>> = notesDao.getAllNotes()
}