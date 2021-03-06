package ru.samarin.kotlinapp.ui.main

import android.arch.lifecycle.Observer
import ru.samarin.kotlinapp.data.NotesRepository
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.model.NoteResult
import ru.samarin.kotlinapp.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {

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

    private val repositoryNotes = NotesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(noteObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(noteObserver)
        super.onCleared()
    }
}