package com.prologistik.test.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prologistik.test.data.database.TestDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<TestDatabase> {
    val dbFile = context.getDatabasePath("test.db")
    return Room.databaseBuilder<TestDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
}