package com.prologistik.test.data.repository

import com.prologistik.test.data.remote.services.TestService

class TestRepository(private val service: TestService){
    suspend fun getPosts() = service.getPosts()
}