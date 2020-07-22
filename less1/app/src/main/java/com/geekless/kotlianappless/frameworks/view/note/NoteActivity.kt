package com.geekless.kotlianappless.frameworks.view.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.geekless.kotlianappless.R
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.activity_note.toolbar
import androidx.lifecycle.Observer
import com.geekless.kotlia.NoteViewModel
import com.geekless.kotlianappless.interface_adapters.viewmodel.note.NoteViewState
import com.google.firebase.auth.FirebaseAuth

class NoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NOTE = "extra.NOTE"

        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)

        val noteViewModelFactory = NoteViewModelFactory(this)
        noteViewModel = ViewModelProviders.of(this, noteViewModelFactory)[NoteViewModel::class.java]
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        noteViewModel.viewNote().observe(this, Observer { setData(it) })
        val noteId = intent.getStringExtra(EXTRA_NOTE)
        noteViewModel.loadNote(noteId)
        initView()
    }

    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = saveNote()
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    fun saveNote() {
        val title = et_title.text.toString()
        val text = et_body.text.toString()
        noteViewModel.save(title, text);
    }

    fun setData(viewState: NoteViewState) {
        viewState?.let {
            supportActionBar?.title = it.toolbarTitle
            et_title.setText(it.title)
            et_body.setText(it.text)
            toolbar.setBackgroundColor(it.toolbarColor)
        }
    }

    fun initView() {
        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
        val user =FirebaseAuth.getInstance().currentUser
        val str=user?.displayName
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}






