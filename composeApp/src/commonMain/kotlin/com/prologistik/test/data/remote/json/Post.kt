package com.prologistik.test.data.remote.json

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
){
    override fun toString() = title
}