package com.prologistik.test.data.remote.services

import com.prologistik.test.data.remote.json.Post
import getResult
import io.ktor.client.HttpClient

class TestService(private val client: HttpClient) {
    suspend fun getPosts() = client.getResult<List<Post>>("posts")
    suspend fun getPost() = client.getResult<Post>("posts/1")
}