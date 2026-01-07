package dev.mazharhussain.smartsms.ui.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dev.mazharhussain.smartsms.ui.Screen
import dev.mazharhussain.smartsms.ui.common.LocalBackStack
import dev.mazharhussain.smartsms.ui.common.push

/** Show [content] when [Manifest.permission.READ_SMS] is granted. Otherwise, request SMS permission */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WithSmsPermission(
    content: @Composable () -> Unit
) = WithPermissionsOrNavigate(
    permissions = listOf(Manifest.permission.READ_SMS),
    destination = Screen.SmsPermission,
    content = content,
)

/** Show [content] when all the [permissions] are granted. Otherwise, navigate away to [destination] */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WithPermissionsOrNavigate(
    permissions: List<String>,
    destination: Screen,
    content: @Composable () -> Unit
) = WithPermissions(
    permissions = permissions,
    content = content,
    onRevoked = {
        val backStack = LocalBackStack.current
        LaunchedEffect(Unit) {
            backStack.push(screen = destination)
        }
    }
)

/** Only show [content] when all the [permissions] are granted. Otherwise, show [onRevoked] */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WithPermissions(
    permissions: List<String>,
    onRevoked: @Composable (List<PermissionState>) -> Unit = {},
    content: @Composable () -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(permissions)

    when {
        permissionsState.allPermissionsGranted -> content()
        else -> onRevoked(permissionsState.revokedPermissions)
    }
}
