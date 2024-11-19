package com.example.practiceproject

import android.app.Application
import com.example.practiceproject.data.loginViewModelModule
import com.example.practiceproject.data.networkModule
import com.example.practiceproject.data.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(networkModule, repoModule, loginViewModelModule)
        }

    }
}