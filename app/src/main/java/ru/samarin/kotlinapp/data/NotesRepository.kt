package ru.samarin.kotlinapp.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ru.samarin.kotlinapp.data.entity.Note
import java.util.*

object NotesRepository {

    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes = mutableListOf(
        Note(
            UUID.randomUUID().toString(),
            "Заголовок",
            "Текст заметки",
            Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заголовок",
            "Текст заметки",
            Note.Color.YELLOW
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заголовок",
            "Текст заметки",
            Note.Color.GREEN
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заголовок",
            "Текст заметки",
            Note.Color.BLUE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заголовок",
            "Текст заметки",
            Note.Color.RED
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заголовок",
            "Текст заметки",
            Note.Color.VIOLET
        )
    )

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

    init {
        notesLiveData.value = notes
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note) {
        for (i in notes.indices) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}