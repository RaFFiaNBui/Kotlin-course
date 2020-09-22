package ru.samarin.kotlinapp.ui.splash

import kotlinx.coroutines.launch
import ru.samarin.kotlinapp.data.NotesRepository
import ru.samarin.kotlinapp.data.errors.NoAuthException
import ru.samarin.kotlinapp.ui.base.BaseViewModel

class SplashViewModel(val notesRepository: NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() = launch {
        notesRepository.getCurrentUser()?.let {
            setData(true)
        } ?: let {
            setError(NoAuthException())
        }
    }
}