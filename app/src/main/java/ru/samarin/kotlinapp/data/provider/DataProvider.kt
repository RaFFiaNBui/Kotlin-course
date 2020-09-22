package ru.samarin.kotlinapp.data.provider

import kotlinx.coroutines.channels.ReceiveChannel
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.entity.User
import ru.samarin.kotlinapp.data.model.NoteResult

interface DataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id: String): Note
    suspend fun saveNote(note: Note): Note
    suspend fun deleteNote(noteId: String)
    suspend fun getCurrentUser(): User?
}