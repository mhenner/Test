package com.prologistik.test.di

import com.prologistik.test.ui.test.TestScreenModel
import org.koin.dsl.module

val testModule = module {
    factory { TestScreenModel() }
}