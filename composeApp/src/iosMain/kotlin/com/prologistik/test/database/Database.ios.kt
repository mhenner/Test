package com.prologistik.test.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.prologistik.test.data.database.TestDatabase

fun getDatabaseBuilder(): RoomDatabase.Builder<TestDatabase> {
    val dbFilePath = documentDirectory() + "/test.db"
    return Room.databaseBuilder<TestDatabase>(
        name = dbFilePath,
    )
}

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}