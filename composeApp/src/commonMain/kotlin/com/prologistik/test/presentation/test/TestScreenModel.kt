package com.prologistik.test.presentation.test

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.prologistik.test.data.repository.TestRepository
import kotlinx.coroutines.launch

class TestScreenModel(private val testRepository: TestRepository) : ScreenModel {

    init {
        getPosts()
    }

    private fun getPosts() = screenModelScope.launch {
        testRepository.getPosts()
            .onSuccess { response ->
                println(response)
            }
            .onFailure { error ->
                println(error)
            }
    }
}