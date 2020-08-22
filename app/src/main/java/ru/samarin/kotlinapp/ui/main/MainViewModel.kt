package ru.samarin.kotlinapp.ui.main

import android.arch.lifecycle.Observer
import android.support.annotation.VisibleForTesting
import ru.samarin.kotlinapp.data.NotesRepository
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.model.NoteResult
import ru.samarin.kotlinapp.ui.base.BaseViewModel

class MainViewModel(notesRepository: NotesRepository) :
    BaseViewModel<List<Note>?, MainViewState>() {

    private val noteObserver = Observer<NoteResult> { result ->
        result ?: return@Observer
        when (result) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = MainViewState(notes = result.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewState(error = result.error)
            }
        }
    }

    private val repositoryNotes = notesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(noteObserver)
    }

    @VisibleForTesting
    public override fun onCleared() {
        repositoryNotes.removeObserver(noteObserver)
        super.onCleared()
    }
}