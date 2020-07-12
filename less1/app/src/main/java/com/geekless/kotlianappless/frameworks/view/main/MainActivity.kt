package com.geekless.kotlianappless.frameworks.view.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.geekless.kotliana.MainViewModel
import com.geekless.kotlianappless.R
import com.geekless.kotlianappless.frameworks.view.note.NoteActivity
import com.geekless.kotlianappless.model.interactors.utility.Utility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_scrolling.*

class MainActivity : FragmentActivity() {
    lateinit var mainViewModel: MainViewModel;
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myViewModelFactory = MainViewModelFactory()
        mainViewModel = ViewModelProviders.of(this, myViewModelFactory)[MainViewModel::class.java]

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter ({
            mainViewModel.setDefaultNote(it)
            NoteActivity.start(this)
        }, Utility(this))
        rv_notes.adapter = adapter
        mainViewModel.viewState().observe(this, Observer { state ->
            state?.let { adapter.notes = it.notes }
        })
        addNoteFab.setOnClickListener {
          mainViewModel.createNote()
          NoteActivity.start(this) }
    }
}


