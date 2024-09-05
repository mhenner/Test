package com.prologistik.test.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.prologistik.test.data.database.dao.TestDao
import com.prologistik.test.data.database.entity.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO



@Database(entities = [Test::class], version = 1)
@ConstructedBy(TestDatabaseConstructor::class)
//@TypeConverters(Converters::class)
abstract class TestDatabase : RoomDatabase(), DB {
    abstract fun testDao(): TestDao
}

interface DB {
    fun clearAllTables(): Unit {}
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TestDatabaseConstructor : RoomDatabaseConstructor<TestDatabase>

fun getTestDatabase(
    builder: RoomDatabase.Builder<TestDatabase>
): TestDatabase {
    return builder
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}