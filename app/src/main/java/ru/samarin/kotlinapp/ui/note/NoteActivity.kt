package ru.samarin.kotlinapp.ui.note

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.samarin.kotlinapp.R
import ru.samarin.kotlinapp.common.getColorInt
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<NoteData>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "EXTRA.NOTE"
        private const val DATE_FORMAT = "dd.MM.yyyy HH:mm"

        fun start(context: Context, noteId: String? = null) =
            Intent(context, NoteActivity::class.java).run {
                noteId?.let {
                    putExtra(EXTRA_NOTE, noteId)
                }
                context.startActivity(this)
            }
    }

    private var note: Note? = null
    private var color: Note.Color = Note.Color.WHITE
    override val model: NoteViewModel by viewModel()
    override val layoutRes = R.layout.activity_note

    val textChangedListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        noteId?.let { id ->
            model.loadNote(id)
        }
        initView()
    }

    override fun renderData(data: NoteData) {
        if (data.isDeleted) finish()
        this.note = data.note
        initView()
    }

    private fun initView() {
        note_title.removeTextChangedListener(textChangedListener)
        note_body.removeTextChangedListener(textChangedListener)

        note?.let { note ->
            note_title.setText(note.title)
            note_body.setText(note.text)
            note_title.setSelection(note_title.text?.length ?: 0)
            note_body.setSelection(note_body.text?.length ?: 0)
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(note.lastChanged)
            toolbar.setBackgroundColor(note.color.getColorInt(this))
        } ?: let {
            supportActionBar?.title = "Новая заметка"
        }
        note_title.addTextChangedListener(textChangedListener)
        note_body.addTextChangedListener(textChangedListener)

        colorPicker.onColorClickListener = {
            color = it
            toolbar.setBackgroundColor(color.getColorInt(this))
            saveNote()
        }
    }

    private fun saveNote() {
        if (note_title.text == null || note_title.text!!.length < 3) return
        note = note?.copy(
            title = note_title.text.toString(),
            text = note_body.text.toString(),
            lastChanged = Date(),
            color = color
        ) ?: Note(
            UUID.randomUUID().toString(),
            note_title.text.toString(),
            note_body.text.toString()
        )

        note?.let {
            model.save(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) =
        menuInflater.inflate(R.menu.note, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.palette -> togglePalette().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (colorPicker.isOpen) {
            colorPicker.close()
            return
        }
        super.onBackPressed()
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else colorPicker.open()
    }

    private fun deleteNote() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(getString(R.string.yes)) { dialog, which -> model.deleteNote() }
            .setNegativeButton(getString(R.string.no)) { dialog, which -> dialog.dismiss() }
            .show()
    }
}