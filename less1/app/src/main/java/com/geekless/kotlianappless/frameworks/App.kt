package com.geekless.kotlianappless.frameworks

import android.app.Application
import org.koin.core.context.startKoin
import ru.geekbrains.gb_kotlin.di.appModule
import ru.geekbrains.gb_kotlin.di.mainModule
import ru.geekbrains.gb_kotlin.di.noteModule
import ru.geekbrains.gb_kotlin.di.splashModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(appModule, splashModule, mainModule, noteModule) }
    }
}