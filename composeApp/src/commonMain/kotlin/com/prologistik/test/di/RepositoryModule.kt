package com.prologistik.test.di

import com.prologistik.test.data.repository.TestRepository
import org.koin.dsl.module

val repositoryeModule = module {
    single { TestRepository(get()) }
}