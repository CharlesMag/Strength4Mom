package com.example.strength4mom

import android.app.Application
import android.util.Log
import com.example.strength4mom.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("App", "Koin is starting...")
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}