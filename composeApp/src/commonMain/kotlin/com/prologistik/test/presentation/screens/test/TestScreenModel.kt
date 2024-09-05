package com.prologistik.test.presentation.test

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.prologistik.test.data.database.entity.Test
import com.prologistik.test.data.remote.json.Post
import com.prologistik.test.data.repository.TestRepository
import dev.jordond.compass.Location
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TestScreenModel(private val testRepository: TestRepository) : ScreenModel {
    private val geolocator: Geolocator = Geolocator.mobile()
    private val _uiState = MutableStateFlow<TestUiState>(TestUiState.Loading)
    val uiState = _uiState
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TestUiState.Loading
        )

    init {
        addTests()
        getPosts()
        getTests()
        getLocation()
    }

    private fun getLocation() = screenModelScope.launch {
        geolocator.current()
            .onSuccess { location ->
                _uiState.update { uiState ->
                    if (uiState !is TestUiState.Success) {
                        TestUiState.Success(location = location)
                    } else {
                        uiState.copy(location = location)
                    }
                }
            }
            .onFailed { error ->
                _uiState.update { TestUiState.Error(error.message) }
            }
    }

    private fun getPosts() = screenModelScope.launch {
        testRepository.getPosts()
            .onSuccess { posts ->
                _uiState.update { uiState ->
                    if (uiState !is TestUiState.Success) {
                        TestUiState.Success(posts = posts)
                    } else {
                        uiState.copy(posts = posts)
                    }
                }
            }
            .onFailure { error ->
                _uiState.update { TestUiState.Error(error.message) }
            }
    }

    private fun getTests() = screenModelScope.launch {
        val tests = testRepository.getAllTests()
        _uiState.update { uiState ->
            if (uiState !is TestUiState.Success) {
                TestUiState.Success(tests = tests)
            } else {
                uiState.copy(tests = tests)
            }
        }
    }

    private fun addTests() = screenModelScope.launch {
        if (testRepository.isEmpty()) {
            testRepository.insertTest(Test(name = "Test DB 1"))
            testRepository.insertTest(Test(name = "Test DB 2"))
            testRepository.insertTest(Test(name = "Test DB 3"))
        }
    }
}

sealed interface TestUiState {
    data object Loading : TestUiState
    data class Success(
        val tests: List<Test> = listOf(),
        val posts: List<Post> = listOf(),
        val location: Location? = null,
    ) : TestUiState

    data class Error(val error: String?) : TestUiState
}