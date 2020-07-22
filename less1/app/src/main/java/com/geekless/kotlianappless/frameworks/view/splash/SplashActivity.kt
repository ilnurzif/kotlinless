package com.geekless.kotlianappless.frameworks.view.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.geekless.kotliappless.SplashViewModel
import kotlinx.android.synthetic.main.activity_main.*


class SplashActivity : AppCompatActivity() {
    val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this)[SplashViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        viewModel.viewState().observe(this, Observer { state ->
            state.error  ?.let {
                renderError(it)
            } ?: renderData(state.authenticated)
        })
    }

    protected fun renderError(error: Throwable?) {

    }

   fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    override fun onResume() {
        super.onResume()
      //  viewModel.requestUser()
    }

    private fun startMainActivity(){
      //  MainActivity.start(this)
        finish()
    }
}