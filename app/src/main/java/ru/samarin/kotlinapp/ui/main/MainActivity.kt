package ru.samarin.kotlinapp.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.samarin.kotlinapp.R

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter()
        recyclerView.adapter = adapter

        viewModel.viewState().observe(this, Observer { state ->
            state?.let { state ->
                adapter.notes = state.notes
            }
        })
    }
}
