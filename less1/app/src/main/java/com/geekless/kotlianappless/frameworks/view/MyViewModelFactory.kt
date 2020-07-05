package com.geekless.kotlianappless.frameworks.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geekless.kotlianappless.interface_adapters.viewmodel.MyViewModel
import com.geekless.kotlianappless.model.data.MyDataSource
import com.geekless.kotlianappless.model.data.MyRepositoryImpl
import com.geekless.kotlianappless.model.interactors.IModel
import com.geekless.kotlianappless.model.interactors.ModelImpl

class MyViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val myDataSource= MyDataSource()
        val myRepository=MyRepositoryImpl(myDataSource)
        val myModel: IModel = ModelImpl(myRepository)
        return MyViewModel(myModel) as T
    }
}