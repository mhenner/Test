package com.prologistik.test.di

import com.prologistik.test.presentation.test.TestScreenModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { TestScreenModel(get()) }
}