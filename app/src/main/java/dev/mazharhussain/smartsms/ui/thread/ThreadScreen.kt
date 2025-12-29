package dev.mazharhussain.smartsms.ui.thread

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dev.mazharhussain.smartsms.R
import dev.mazharhussain.smartsms.data.Message
import dev.mazharhussain.smartsms.data.mockList
import dev.mazharhussain.smartsms.ui.Screen
import dev.mazharhussain.smartsms.ui.conversations.ConversationList
import dev.mazharhussain.smartsms.ui.permission.WithSmsPermission
import dev.mazharhussain.smartsms.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ThreadScreen(params: Screen.Thread) = WithSmsPermission {
        val viewModel = koinViewModel<ThreadViewModel> { parametersOf(params) }
        val threads by viewModel.messages.collectAsStateWithLifecycle()
        ScreenContent(threads)
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
private fun ThreadScreenPreview() {
    AppTheme { ScreenContent(threads = Message.mockList) }
}
