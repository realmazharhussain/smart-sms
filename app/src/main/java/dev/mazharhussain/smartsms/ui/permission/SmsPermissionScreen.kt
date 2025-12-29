package dev.mazharhussain.smartsms.ui.permission

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import dev.mazharhussain.smartsms.BuildConfig
import dev.mazharhussain.smartsms.ui.common.LocalBackStack
import dev.mazharhussain.smartsms.ui.Screen
import dev.mazharhussain.smartsms.ui.common.pop
import dev.mazharhussain.smartsms.ui.theme.AppTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SmsPermissionScreen() {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.READ_SMS,
        previewPermissionStatus = PermissionStatus.Denied(shouldShowRationale = true),
    )
    val permissionStatus = permissionState.status

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionStatus is PermissionStatus.Denied) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "SMS permission is required for this app to function!",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(300.dp)
            )

            if (permissionStatus.shouldShowRationale) {
                Button(onClick = permissionState::launchPermissionRequest) {
                    Text("Grant Permission")
                }
            } else {
                Button(onClick = {
                    Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = "package:${BuildConfig.APPLICATION_ID}".toUri()
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }.also { intent ->
                        context.startActivity(intent)
                    }
                }) {
                    Text("Open Settings")
                }
            }
        }
    } else {
        val backStack = LocalBackStack.current
        LaunchedEffect(Unit) {
            backStack.pop(Screen.SmsPermission)
        }
    }
}

@Preview
@Composable
private fun SmsPermissionScreenPreview() {
    AppTheme {
        SmsPermissionScreen()
    }
}