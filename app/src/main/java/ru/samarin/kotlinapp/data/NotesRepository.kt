package ru.samarin.kotlinapp.data

import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.provider.FireStoreDataProvider
import ru.samarin.kotlinapp.data.provider.RemoteDataProvider

object NotesRepository {
    val remoteProvider: RemoteDataProvider = FireStoreDataProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}