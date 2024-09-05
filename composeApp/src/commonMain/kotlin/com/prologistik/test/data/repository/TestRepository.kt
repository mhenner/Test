package com.prologistik.test.data.repository

import com.prologistik.test.data.database.TestDatabase
import com.prologistik.test.data.database.entity.Test
import com.prologistik.test.data.remote.services.TestService

class TestRepository(private val service: TestService, private val testDatabase: TestDatabase){
    suspend fun getPosts() = service.getPosts()

    fun getAllTestsAsFlow() = testDatabase.testDao().getAllTestsAsFlow()

    suspend fun getAllTests() = testDatabase.testDao().getAllTests()

    suspend fun insertTest(test: Test) = testDatabase.testDao().insert(test)

    suspend fun isEmpty() = testDatabase.testDao().isEmpty()
}