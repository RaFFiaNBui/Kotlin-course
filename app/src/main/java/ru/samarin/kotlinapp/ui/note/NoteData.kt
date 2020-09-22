package ru.samarin.kotlinapp.ui.note

import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.ui.base.BaseViewState

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)