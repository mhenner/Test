package com.prologistik.test.di

import androidx.room.RoomDatabase
import com.prologistik.test.data.database.TestDatabase
import com.prologistik.test.database.getDatabaseBuilder
import org.koin.dsl.module

actual val platformModule = module {
    single<RoomDatabase.Builder<TestDatabase>> {
        getDatabaseBuilder()
    }
}