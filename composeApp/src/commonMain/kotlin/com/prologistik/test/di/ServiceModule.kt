package com.prologistik.test.di

import com.prologistik.test.data.remote.services.TestService
import org.koin.dsl.module

val serviceModule = module {
    single { TestService(get()) }
}