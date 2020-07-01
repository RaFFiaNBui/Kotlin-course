package ru.samarin.kotlinapp.ui.note

import ru.samarin.kotlinapp.data.NotesRepository
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.model.NoteResult
import ru.samarin.kotlinapp.ui.base.BaseViewModel

class NoteViewModel(val notesRepository: NotesRepository) :
    BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    fun loadNote(noteId: String) {
        notesRepository.getNoteById(noteId).observeForever { result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value =
                        NoteViewState(NoteViewState.Data(note = result.data as? Note))
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            notesRepository.saveNote(it)
        }
    }

    fun deleteNote() {
        pendingNote?.let {
            notesRepository.deleteNote(it.id).observeForever { result ->
                result ?: return@observeForever
                when (result) {
                    is NoteResult.Success<*> -> viewStateLiveData.value =
                        NoteViewState(NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> viewStateLiveData.value =
                        NoteViewState(error = result.error)
                }
            }
        }
    }
}