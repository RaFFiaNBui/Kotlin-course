package ru.samarin.kotlinapp.ui.note

import ru.samarin.kotlinapp.data.NotesRepository
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.model.NoteResult
import ru.samarin.kotlinapp.ui.base.BaseViewModel

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String) {
        NotesRepository.getNoteById(noteId).observeForever { result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = NoteViewState(note = result.data as? Note)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}