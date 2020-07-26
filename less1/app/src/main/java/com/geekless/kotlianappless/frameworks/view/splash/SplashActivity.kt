package com.geekless.kotlianappless.frameworks.view.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.geekless.kotliana.MainViewModel
import com.geekless.kotlianappless.R
import com.geekless.kotlianappless.frameworks.view.main.MainActivity
import com.geekless.kotliappless.SplashViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : AppCompatActivity() {
    companion object {
        private const val RC_SIGNIN = 4242
    }

     val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        viewModel.viewState().observe(this, Observer { state ->
           state.authApiError?.let { renderAuthApiError(it) }
           state.error ?.let {
                renderError(it)
            } ?: renderData(state.authenticated)
        })
    }

    private fun renderAuthApiError(errMsg: String)  {
       Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show()
    }

    protected fun renderError(error: Throwable?) {
        startLogin()
    }

   fun renderData(data: Boolean?) {
            startMainActivity()
    }

    private fun startMainActivity(){
        MainActivity.start(this)
        finish()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_SIGNIN && resultCode != Activity.RESULT_OK){
            startMainActivity()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}