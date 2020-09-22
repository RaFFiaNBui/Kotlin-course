package ru.samarin.kotlinapp.ui.main

import android.support.annotation.VisibleForTesting
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.samarin.kotlinapp.data.NotesRepository
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.data.model.NoteResult
import ru.samarin.kotlinapp.ui.base.BaseViewModel

class MainViewModel(notesRepository: NotesRepository) : BaseViewModel<List<Note>?>() {

    private val notesChannel = notesRepository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when (it) {
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        notesChannel.cancel()
        super.onCleared()
    }
}