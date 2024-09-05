package com.prologistik.test.di

import com.prologistik.test.data.database.getTestDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { getTestDatabase(get()) }
}