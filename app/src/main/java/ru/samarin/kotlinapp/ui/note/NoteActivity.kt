package ru.samarin.kotlinapp.ui.note

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import ru.samarin.kotlinapp.R
import ru.samarin.kotlinapp.data.entity.Note
import ru.samarin.kotlinapp.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

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
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }
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
            viewModel.loadNote(id)
        } ?: let {
            supportActionBar?.title = "Новая заметка"
        }
        initView()
    }

    override fun renderData(data: Note?) {
        this.note = data
        supportActionBar?.title = note?.let { note ->
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(note.lastChanged)
        } ?: let {
            "Новая заметка"
        }
        initView()
    }

    private fun initView() {
        note_title.removeTextChangedListener(textChangedListener)
        note_body.removeTextChangedListener(textChangedListener)

        note?.let { note ->
            note_title.setText(note.title)
            note_body.setText(note.text)

            val color = when (note.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.GREEN -> R.color.green
                Note.Color.VIOLET -> R.color.violet
            }
            toolbar.setBackgroundColor(color)
        }
        note_title.addTextChangedListener(textChangedListener)
        note_body.addTextChangedListener(textChangedListener)
    }

    private fun saveNote() {
        if (note_title.text == null || note_title.text!!.length < 3) return
        note = note?.copy(
            title = note_title.text.toString(),
            text = note_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(
            UUID.randomUUID().toString(),
            note_title.text.toString(),
            note_body.text.toString()
        )

        note?.let {
            viewModel.save(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}