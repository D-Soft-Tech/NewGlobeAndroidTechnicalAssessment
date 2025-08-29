package com.bridge.androidtechnicaltest

import android.app.Application

class App : Application() {

    // private val appComponent : MutableList<Module> = mutableListOf(networkModule, databaseModule)

    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidContext(applicationContext)
//            modules(appComponent)
//        }
    }
}