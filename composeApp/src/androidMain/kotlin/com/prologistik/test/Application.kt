package com.prologistik.test

import android.app.Application
import com.prologistik.test.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class Application : Application(), KoinComponent {
    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        startKoin {
            androidContext(applicationContext)
            modules(appModule())
        }
    }
}