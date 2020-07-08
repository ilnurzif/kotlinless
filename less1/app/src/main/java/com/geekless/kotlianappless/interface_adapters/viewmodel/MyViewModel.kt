package com.geekless.kotliana

import com.geekless.kotlianappless.interface_adapters.viewmodel.MyViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.model.interactors.IModel

class MyViewModel(model: IModel): ViewModel() {
    private val viewStateData = MutableLiveData<MyViewState>()

    init {
        model.getData().subscribe{list-> viewStateData.value=MyViewState(list)}
    }

    fun viewState(): LiveData<MyViewState> = viewStateData
}


