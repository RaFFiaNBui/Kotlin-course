package ru.samarin.kotlinapp.data

import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.provider.DataProvider

class NotesRepository(val remoteProvider: DataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    suspend fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    suspend fun deleteNote(id: String) = remoteProvider.deleteNote(id)
    suspend fun saveNote(note: Note) = remoteProvider.saveNote(note)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
}