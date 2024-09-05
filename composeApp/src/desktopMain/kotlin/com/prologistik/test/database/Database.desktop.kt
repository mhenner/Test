package com.prologistik.test.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.prologistik.test.data.database.TestDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<TestDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "test.db")
    return Room.databaseBuilder<TestDatabase>(
        name = dbFile.absolutePath,
    )
}