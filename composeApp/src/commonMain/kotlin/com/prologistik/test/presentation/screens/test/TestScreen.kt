package com.prologistik.test.presentation.test

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.prologistik.test.components.AlertMessageDialog
import com.prologistik.test.components.ImageSourceOptionDialog
import com.prologistik.test.components.Spinner
import com.prologistik.test.presentation.screens.ErrorScreen
import com.prologistik.test.presentation.screens.LoadingScreen
import com.prologistik.test.utils.PermissionCallback
import com.prologistik.test.utils.PermissionStatus
import com.prologistik.test.utils.PermissionType
import com.prologistik.test.utils.createPermissionsManager
import com.prologistik.test.utils.rememberCameraManager
import com.prologistik.test.utils.rememberGalleryManager
import io.github.joelkanyi.sain.Sain
import io.github.joelkanyi.sain.SignatureAction
import io.github.joelkanyi.sain.SignatureState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.stringResource
import test.composeapp.generated.resources.Res
import test.composeapp.generated.resources.cancel
import test.composeapp.generated.resources.ok
import test.composeapp.generated.resources.permission_required
import test.composeapp.generated.resources.photo_permission_text
import test.composeapp.generated.resources.settings

class TestScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<TestScreenModel>()
        val uiState by screenModel.uiState.collectAsStateWithLifecycle(LocalLifecycleOwner.current)

        TestScreen(uiState = uiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(modifier: Modifier = Modifier, uiState: TestUiState) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(modifier = Modifier.fillMaxWidth(), title = { Text("Text") }, actions = {})
            when (uiState) {
                is TestUiState.Success -> {
                    var text by remember { mutableStateOf("") }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier,
                                text = "Location",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                modifier = Modifier,
                                text = "${uiState.location?.coordinates?.latitude} / ${uiState.location?.coordinates?.longitude}"
                            )
                        }
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

                        Spinner(
                            modifier = Modifier.fillMaxWidth(),
                            label = "Spinner API",
                            items = uiState.posts,
                            onItemSelected = {})

                        Spinner(
                            modifier = Modifier.fillMaxWidth(),
                            label = "Spinner DB",
                            items = uiState.tests,
                            onItemSelected = {})

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                showDatePicker = true
                            }
                        )
                        {
                            Text(text = "Pick a Date", style = MaterialTheme.typography.titleLarge)
                        }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                showTimePicker = true
                            }
                        )
                        {
                            Text(text = "Pick a Time", style = MaterialTheme.typography.titleLarge)
                        }


                        TakePhotoScreen()

                        var imageBitmap: ImageBitmap? by remember { mutableStateOf(null) }
                        val state = remember { SignatureState() }

                        Sain(
                            state = state,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .border(
                                    BorderStroke(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            onComplete = { signatureBitmap ->
                                if (signatureBitmap != null) {
                                    imageBitmap = signatureBitmap
                                } else {
                                    println("Signature is empty")
                                }
                            },
                        ) { action ->
                            Row(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Button(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        imageBitmap = null
                                        action(SignatureAction.CLEAR)
                                    }) {
                                    Text(
                                        text = "Clear",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                                Button(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        action(SignatureAction.COMPLETE)
                                    }) {
                                    Text(
                                        text = "Complete",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                        }
                    }
                }

                TestUiState.Loading -> LoadingScreen()
                is TestUiState.Error -> ErrorScreen(error = uiState.error)
            }
        }

        val datePickerState = rememberDatePickerState()
        if (showDatePicker) {
            DatePickerDialog(
                modifier = Modifier.align(Alignment.Center),
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(text = stringResource(Res.string.ok))
                    }
                }) {
                DatePicker(state = datePickerState)
            }
        }

        val timePickerState = rememberTimePickerState()
        if (showTimePicker) {
            DatePickerDialog(
                modifier = Modifier.align(Alignment.Center),
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = { showTimePicker = false }) {
                        Text(text = stringResource(Res.string.ok))
                    }
                }) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp),
                        text = "Select time", style = MaterialTheme.typography.labelLarge
                    )
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                        TimePicker(
                            modifier = Modifier.align(Alignment.Center),
                            state = timePickerState
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TakePhotoScreen(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val imageBitmaps = remember { mutableStateListOf<ImageBitmap>() }
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCamera = true
                        PermissionType.GALLERY -> launchGallery = true
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }


    })

    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            bitmap?.let { bitmap ->
                imageBitmaps.add(bitmap)
            }
        }
    }

    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            bitmap?.let { bitmap ->
                imageBitmaps.add(bitmap)
            }
        }
    }

    if (imageSourceOptionDialog) {
        ImageSourceOptionDialog(onDismissRequest = {
            imageSourceOptionDialog = false
        }, onGalleryRequest = {
            imageSourceOptionDialog = false
            launchGallery = true
        }, onCameraRequest = {
            imageSourceOptionDialog = false
            launchCamera = true
        })
    }
    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery = false
    }
    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        launchCamera = false
    }
    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }

    if (permissionRationalDialog) {
        AlertMessageDialog(title = stringResource(Res.string.permission_required),
            message = stringResource(Res.string.photo_permission_text),
            positiveButtonText = stringResource(Res.string.settings),
            negativeButtonText = stringResource(Res.string.cancel),
            onPositiveClick = {
                permissionRationalDialog = false
                launchSetting = true

            },
            onNegativeClick = {
                permissionRationalDialog = false
            })
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Button(modifier = Modifier.fillMaxWidth(), onClick = { imageSourceOptionDialog = true }) {
            Text("Add Photo", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp),
            columns = GridCells.Adaptive(minSize = 92.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(imageBitmaps) { bitmap ->
                Image(modifier = Modifier, bitmap = bitmap, contentDescription = null)
            }
        }
    }

}