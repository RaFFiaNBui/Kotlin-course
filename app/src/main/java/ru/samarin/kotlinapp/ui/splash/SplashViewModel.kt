package ru.samarin.kotlinapp.ui.splash

import ru.samarin.kotlinapp.data.NotesRepository
import ru.samarin.kotlinapp.data.errors.NoAuthException
import ru.samarin.kotlinapp.ui.base.BaseViewModel

class SplashViewModel(val notesRepository: NotesRepository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}