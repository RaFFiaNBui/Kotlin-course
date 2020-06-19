package ru.samarin.kotlinapp.ui.note

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import ru.samarin.kotlinapp.R
import ru.samarin.kotlinapp.data.entity.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "EXTRA.NOTE"
        private const val DATE_FORMAT = "dd.MM.yyyy HH:mm"

        fun start(context: Context, note: Note? = null) =
            Intent(context, NoteActivity::class.java).run {
                note?.let {
                    putExtra(EXTRA_NOTE, note)
                }
                context.startActivity(this)
            }
    }

    private var note: Note? = null
    lateinit var viewModel: NoteViewModel

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
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)
        note = intent.getParcelableExtra(EXTRA_NOTE)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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