package dev.mazharhussain.smartsms.ui.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dev.mazharhussain.smartsms.ui.Screen
import dev.mazharhussain.smartsms.ui.common.LocalBackStack
import dev.mazharhussain.smartsms.ui.common.push

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WithSmsPermission(content: @Composable () -> Unit) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.READ_SMS)
    if (permissionState.status.isGranted) {
        content()
        return
    }

    val backStack = LocalBackStack.current
    LaunchedEffect(Unit) {
        backStack.push(Screen.SmsPermission)
    }
}
