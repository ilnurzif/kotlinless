package ru.geekbrains.gb_kotlin.di

import com.geekless.kotlia.NoteViewModel
import com.geekless.kotliana.MainViewModel
import com.geekless.kotlianappless.frameworks.firebase.FireBaseDataSource
import com.geekless.kotlianappless.model.data.BaseRepositoryImpl
import com.geekless.kotlianappless.model.data.IDataSource
import com.geekless.kotlianappless.model.interactors.main.IMainModel
import com.geekless.kotlianappless.model.interactors.main.MainModelImpl
import com.geekless.kotlianappless.model.interactors.note.INoteModel
import com.geekless.kotlianappless.model.interactors.note.NodeModelImpl
import com.geekless.kotlianappless.model.interactors.splash.ISplashModel
import com.geekless.kotlianappless.model.interactors.splash.SplashModelImpl
import com.geekless.kotlianappless.model.repositories.IMyRepository
import com.geekless.kotliappless.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<IDataSource> { FireBaseDataSource(get(), get()) }
    single<IMyRepository> { BaseRepositoryImpl(get()) }
}

val splashModule = module {
    factory <ISplashModel> { SplashModelImpl(get())}
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    factory<IMainModel> { MainModelImpl(get()) }
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    factory <INoteModel> { NodeModelImpl(get()) }
    viewModel { NoteViewModel(get()) }
}