package dev.mazharhussain.smartsms.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.mazharhussain.smartsms.ui.common.instantTransform
import dev.mazharhussain.smartsms.ui.common.rememberNavBackStackNavEntryDecorator
import dev.mazharhussain.smartsms.ui.common.rememberSsNavBackStack
import dev.mazharhussain.smartsms.ui.conversations.ConversationsScreen
import dev.mazharhussain.smartsms.ui.permission.SmsPermissionScreen
import dev.mazharhussain.smartsms.ui.thread.ThreadScreen
import kotlinx.serialization.Serializable

sealed interface Screen: NavKey {
    @Serializable data object SmsPermission : Screen
    @Serializable data object Conversations : Screen
    @Serializable data class Thread(val threadId: Long) : Screen
}

@Composable
fun SsAppUi() {
    val backStack = rememberSsNavBackStack(initialScreen = Screen.Conversations)

    NavDisplay(
        backStack = backStack,
        transitionSpec = { slideInHorizontally { it / 2 } togetherWith fadeOut(targetAlpha = 1f) },
        popTransitionSpec = { fadeIn(initialAlpha = 1f) togetherWith slideOutHorizontally { it / 2 } },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberNavBackStackNavEntryDecorator(
                backStack
            ),
        ),
        entryProvider = entryProvider {
            entry<Screen.Conversations> {
                ConversationsScreen()
            }

            entry<Screen.Thread> { params ->
                ThreadScreen(params)
            }

            entry<Screen.SmsPermission>(
                metadata = NavDisplay.transitionSpec { instantTransform() }
                        + NavDisplay.popTransitionSpec { instantTransform() }
            ) {
                SmsPermissionScreen()
            }
        }
    )
}
