package com.geekless.kotlianappless.frameworks.view.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.geekless.kotlianappless.R
import com.geekless.kotlianappless.frameworks.view.main.MainActivity
import com.geekless.kotliappless.SplashViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class SplashActivity : AppCompatActivity(), CoroutineScope {
    companion object {
        private const val RC_SIGNIN = 4242
    }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            viewModel.getViewState().consumeEach {
                renderData(it)
            }
        }

        errorJob = launch {
            viewModel.getErrorChannel().consumeEach {
                renderError(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    private fun renderAuthApiError(errMsg: String) {
        Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show()
    }

    protected fun renderError(error: Throwable?) {
        startLogin()
    }

    fun renderData(data: Boolean?) {
        startMainActivity()
    }

    private fun startMainActivity() {
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
        if (requestCode == RC_SIGNIN && resultCode != Activity.RESULT_OK) {
            startMainActivity()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}