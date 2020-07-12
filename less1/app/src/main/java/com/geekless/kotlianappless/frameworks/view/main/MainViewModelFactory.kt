package com.geekless.kotlianappless.frameworks.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geekless.kotliana.MainViewModel
import com.geekless.kotlianappless.model.data.BaseDataSource
import com.geekless.kotlianappless.model.data.BaseRepositoryImpl
import com.geekless.kotlianappless.model.interactors.main.IMainModel
import com.geekless.kotlianappless.model.interactors.main.MainModelImpl

class MainViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val myRepository=BaseRepositoryImpl(BaseDataSource)
        val myMainModel: IMainModel = MainModelImpl(myRepository)
        return MainViewModel(myMainModel) as T
    }
}