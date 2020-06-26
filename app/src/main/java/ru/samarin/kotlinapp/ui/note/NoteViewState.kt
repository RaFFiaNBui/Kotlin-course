package ru.samarin.kotlinapp.ui.note

import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.ui.base.BaseViewState

class NoteViewState(val note: Note? = null, error: Throwable? = null) :
    BaseViewState<Note?>(note, error)