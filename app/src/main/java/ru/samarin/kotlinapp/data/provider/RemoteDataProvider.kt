package ru.samarin.kotlinapp.data.provider

import android.arch.lifecycle.LiveData
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.entity.User
import ru.samarin.kotlinapp.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>
}