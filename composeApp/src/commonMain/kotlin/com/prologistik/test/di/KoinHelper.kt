package com.prologistik.test.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}