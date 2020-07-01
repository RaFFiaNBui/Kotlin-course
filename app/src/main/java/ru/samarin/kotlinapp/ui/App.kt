package ru.samarin.kotlinapp.ui

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.samarin.kotlinapp.data.di.appModule
import ru.samarin.kotlinapp.data.di.mainModule
import ru.samarin.kotlinapp.data.di.noteModule
import ru.samarin.kotlinapp.data.di.splashModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}