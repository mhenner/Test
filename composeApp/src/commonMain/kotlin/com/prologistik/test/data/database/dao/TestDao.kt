package com.prologistik.test.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.prologistik.test.data.database.entity.Test
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Insert
    suspend fun insert(item: Test)

    @Insert
    suspend fun insert(items: List<Test>)

    @Query("SELECT * FROM test")
    fun getAllTestsAsFlow(): Flow<List<Test>>

    @Query("SELECT * FROM test")
    suspend fun getAllTests(): List<Test>

    @Query("SELECT (SELECT COUNT(*) FROM test) == 0")
    suspend fun isEmpty(): Boolean
}