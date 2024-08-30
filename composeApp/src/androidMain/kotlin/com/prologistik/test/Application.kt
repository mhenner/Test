package com.prologistik.test

import android.app.Application
import com.prologistik.test.di.initKoin
import org.koin.core.component.KoinComponent

class Application : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}