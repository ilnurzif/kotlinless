package com.geekless.kotlianappless.frameworks.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import com.geekless.kotliana.MainViewModel
import com.geekless.kotlianappless.R
import com.geekless.kotlianappless.frameworks.view.note.NoteActivity
import com.geekless.kotlianappless.frameworks.view.splash.SplashActivity
import com.geekless.kotlianappless.model.interactors.utility.Utility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    val mainViewModel: MainViewModel by viewModel()
    lateinit var adapter: NotesRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter({ NoteActivity.start(this, it.id) }, Utility(this))
        rv_notes.adapter = adapter
        mainViewModel.viewState().observe(this, Observer { state ->
            state.error?.let { renderError(it) } ?: state?.notes?.let { adapter.notes = it }
        })

        addNoteFab.setOnClickListener {
            NoteActivity.start(this)
        }

    }

    private fun renderError(err: Throwable) {
        Toast.makeText(this, err.message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logout -> showLogoutDialog().let { true }
        else -> false
    }


    private fun showLogoutDialog() {
        val logoutDialog = LogoutDialog.createInstance()
        logoutDialog.publishSubject.subscribe { res ->
            res.takeIf { res == true }.let { onLogout() }
        }
        logoutDialog.show(supportFragmentManager, LogoutDialog.TAG)
    }

    fun onLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }
    }

}


