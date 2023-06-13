package com.example.challenge

import android.app.Application
import com.example.challenge.di.injectFeature
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // CONFIGURATION ---
    private fun configureDi() =
        startKoin {
            androidContext(this@BaseApplication)
            injectFeature()
        }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}
