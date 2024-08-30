package com.prologistik.test

import androidx.compose.ui.window.ComposeUIViewController
import com.prologistik.test.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
    initKoin()
}) { App() }