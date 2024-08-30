package com.prologistik.test.ui.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.prologistik.test.components.Spinner

@OptIn(ExperimentalMaterial3Api::class)
class TestScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<TestScreenModel>()
        var text by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(modifier = Modifier.fillMaxWidth(), title = { Text("Text") }, actions = {

            })
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("TextField") },
                    value = text,
                    onValueChange = { text = it })

                Spinner(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Spinner",
                    items = listOf("Test1", "Test2", "Test3", "Test4", "Test5"),
                    onItemSelected = {})

                Button(modifier = Modifier.fillMaxWidth(), onClick = {}) {
                    Text("Take Photo", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}