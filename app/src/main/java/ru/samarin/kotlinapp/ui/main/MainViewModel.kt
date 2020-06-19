package ru.samarin.kotlinapp.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.samarin.kotlinapp.data.NotesRepository

class MainViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        NotesRepository.getNotes().observeForever { notes ->
            notes?.let { notes ->
                viewStateLiveData.value =
                    viewStateLiveData.value?.copy(notes = notes) ?: MainViewState(notes)
            }
        }
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}