package com.geekless.kotlianappless.frameworks.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import com.geekless.kotliana.MainViewModel
import com.geekless.kotlianappless.R
import com.geekless.kotlianappless.frameworks.view.note.NoteActivity
import com.geekless.kotlianappless.frameworks.view.splash.SplashActivity
import com.geekless.kotlianappless.model.interactors.utility.Utility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_scrolling.*


class MainActivity : AppCompatActivity(), LogoutDialog.LogoutListener {
    companion object {
        private const val RC_SIGNIN = 4242
    }

    lateinit var mainViewModel: MainViewModel;
    lateinit var adapter: NotesRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val myViewModelFactory = MainViewModelFactory()
        mainViewModel = ViewModelProviders.of(this, myViewModelFactory)[MainViewModel::class.java]

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter({ NoteActivity.start(this, it.id)}, Utility(this))
        rv_notes.adapter = adapter
        mainViewModel.viewState().observe(this, Observer { state ->state.error?.let {renderError(it)} ?: state?.notes?.let {adapter.notes = it}})

        addNoteFab.setOnClickListener {
            NoteActivity.start(this)
        }
        startLogin()
    }

    private fun renderError(err: Throwable) {
        Toast.makeText(this, err.message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logout -> showLogoutDialog().let { true }
        else -> false
    }

    fun startLogin() {
        val providers = listOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.android_robot)
                        .setTheme(R.style.LoginStyle)
                        .setAvailableProviders(providers)
                        .build()
                , RC_SIGNIN
        )
    }

    private fun showLogoutDialog(){
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
        LogoutDialog.createInstance().show(supportFragmentManager, LogoutDialog.TAG)

    }

    override fun onLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_SIGNIN && resultCode != Activity.RESULT_OK){
            finish()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}


