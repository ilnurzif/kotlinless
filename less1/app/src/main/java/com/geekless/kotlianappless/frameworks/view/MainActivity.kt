package com.geekless.kotlianappless.frameworks.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.geekless.kotliana.MyViewModel
import com.geekless.kotlianappless.R
import kotlinx.android.synthetic.main.content_scrolling.*


class MainActivity : FragmentActivity() {
    lateinit var myViewModel: MyViewModel;
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myViewModelFactory = MyViewModelFactory()
        myViewModel = ViewModelProviders.of(this, myViewModelFactory)[MyViewModel::class.java]

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter()
        rv_notes.adapter = adapter
        myViewModel.viewState().observe(this, Observer { state ->
            state?.let { adapter.notes = it.notes }
        })
    }
}

