package ru.samarin.kotlinapp.data

import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.provider.DataProvider

class NotesRepository(val remoteProvider: DataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun deleteNote(id: String) = remoteProvider.deleteNote(id)
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}