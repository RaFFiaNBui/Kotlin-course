package ru.samarin.kotlinapp.data

import ru.samarin.kotlinapp.data.entity.Note

object NotesRepository {

    private val notes: List<Note> = listOf(
        Note(
            "Заголовок",
            "Текст заметки",
            0xfff06292.toInt()
        ),
        Note(
            "Заголовок",
            "Текст заметки",
            0xff9575cd.toInt()
        ),
        Note(
            "Заголовок",
            "Текст заметки",
            0xff64b5f6.toInt()
        ),
        Note(
            "Заголовок",
            "Текст заметки",
            0xff4db6ac.toInt()
        ),
        Note(
            "Заголовок",
            "Текст заметки",
            0xffb2ff59.toInt()
        ),
        Note(
            "Заголовок",
            "Текст заметки",
            0xffffeb3b.toInt()
        )
    )

    fun getNotes(): List<Note> {
        return notes
    }
}