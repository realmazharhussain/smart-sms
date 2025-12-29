package dev.mazharhussain.smartsms.ui.conversations

import android.Manifest
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dev.mazharhussain.smartsms.R
import dev.mazharhussain.smartsms.data.Message
import dev.mazharhussain.smartsms.data.mockList
import dev.mazharhussain.smartsms.ui.common.LocalBackStack
import dev.mazharhussain.smartsms.ui.Screen
import dev.mazharhussain.smartsms.ui.common.push
import dev.mazharhussain.smartsms.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ConversationsScreen() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.READ_SMS)

    if (permissionState.status.isGranted) {
        val viewModel = koinViewModel<ConversationsViewModel>()
        val threads by viewModel.threads.collectAsStateWithLifecycle()
        ScreenContent(threads)
    } else {
        val backStack = LocalBackStack.current
        LaunchedEffect(Unit) {
            backStack.push(Screen.SmsPermission)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(threads: List<Message>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            )
        },
    ) { innerPadding ->
        ConversationList(threads, modifier = Modifier.padding(innerPadding))
    }
}

@Preview
@Composable
private fun ConversationsScreenPreview() {
    AppTheme { ScreenContent(threads = Message.mockList) }
}
