package com.prologistik.test.di

import com.prologistik.test.presentation.test.TestScreenModel
import org.koin.dsl.module

val screenModelModule = module {
    factory { TestScreenModel(get()) }
}