package com.geekless.kotlianappless.interface_adapters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.model.interactors.IModel

class MyViewModel(model: IModel): ViewModel() {
    private val viewStateData = MutableLiveData<String>()

    init {
        model.getData().subscribe{str -> viewStateData.value = str}
    }

    fun viewState(): LiveData<String> =  viewStateData
    fun onClickButt() {viewStateData.value = "butclickText"}
}


