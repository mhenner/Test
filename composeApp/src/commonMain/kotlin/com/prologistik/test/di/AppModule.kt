package com.prologistik.test.di

fun appModule() = listOf(
    platformModule,
    screenModelModule,
    databaseModule,
    networkModule,
    serviceModule,
    repositoryModule,
)