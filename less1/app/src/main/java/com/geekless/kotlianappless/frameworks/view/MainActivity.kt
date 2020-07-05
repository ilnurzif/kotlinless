package com.geekless.kotlianappless.frameworks.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.geekless.kotlianappless.R

import com.geekless.kotlianappless.interface_adapters.viewmodel.MyViewModel

class MainActivity : FragmentActivity() {
    lateinit var myViewModel: MyViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myViewModelFactory = MyViewModelFactory()
        myViewModel = ViewModelProviders.of(this, myViewModelFactory)[MyViewModel::class.java]

        myViewModel.viewState().observe(this, Observer {
           str->Toast.makeText(this,str,Toast.LENGTH_LONG).show();
            findViewById<TextView>(R.id.myTW).text = str
        })

        findViewById<Button>(R.id.button).setOnClickListener { myViewModel.onClickButt() }
    }
}

