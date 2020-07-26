package com.geekless.kotlianappless.frameworks.view.note

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geekless.kotlianappless.R
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.activity_note.toolbar
import androidx.lifecycle.Observer
import com.geekless.kotlia.NoteViewModel
import com.geekless.kotlianappless.frameworks.common.getColorInt
import com.geekless.kotlianappless.interface_adapters.viewmodel.note.NoteViewState
import org.koin.android.viewmodel.ext.android.viewModel


class NoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NOTE = "extra.NOTE"

        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

    val noteViewModel: NoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)

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

    override fun onPause() {
        noteViewModel.pause()
        super.onPause()
    }

    fun setData(viewState: NoteViewState) {
        viewState.delError?.let {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            return
        }

        viewState.saveErr?.let {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            return
        }

        viewState.delOk.takeIf { it == true }?.let {
            Toast.makeText(this, getString(R.string.note_del_title), Toast.LENGTH_LONG).show()
            finish()
            return
        }

        viewState.saveOk.takeIf { it == true }?.let {
            Toast.makeText(this, getString(R.string.save_ok_title), Toast.LENGTH_LONG).show()
            return
        }

        viewState?.let {
            supportActionBar?.title = it.toolbarTitle
            et_title.setText(it.title)
            et_body.setText(it.text)
            it.note?.color?.getColorInt(this)?.let { it1 -> toolbar.setBackgroundColor(it1) }
        }
    }

    fun initView() {
        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
        colorPicker.onColorClickListener = {
            noteViewModel.setColor(it)
            toolbar.setBackgroundColor(it.getColorInt(this))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this).inflate(R.menu.note, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.palette -> togglePallete().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    fun togglePallete() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    fun deleteNote() {
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.note_delete_message))
                .setNegativeButton(getString(R.string.note_delete_cancel)) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(getString(R.string.note_delete_ok)) { dialog, which -> noteViewModel.deleteNote() }
                .show()
    }
}








