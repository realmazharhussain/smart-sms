package dev.mazharhussain.smartsms.ui

import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.mazharhussain.smartsms.ui.conversations.ConversationsScreen
import dev.mazharhussain.smartsms.ui.permission.SmsPermissionScreen
import kotlinx.serialization.Serializable

val LocalBackStack = staticCompositionLocalOf { NavBackStack<Screen>(Screen.Conversations) }

sealed interface Screen: NavKey {
    @Serializable data object Conversations : Screen
    @Serializable data object SmsPermission : Screen
}

@Composable
fun AppUI() {
    @Suppress("UNCHECKED_CAST")
    val backStack = rememberNavBackStack(Screen.Conversations) as NavBackStack<Screen>

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        transitionSpec = { slideInHorizontally { it / 2 } togetherWith fadeOut(targetAlpha = 1f) },
        popTransitionSpec = { fadeIn(initialAlpha = 1f) togetherWith slideOutHorizontally { it / 2 } },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberNavBackStackNavEntryDecorator(backStack),
        ),
        entryProvider = entryProvider {
            entry<Screen.Conversations> {
                ConversationsScreen()
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

fun snapIn() = fadeIn(animationSpec = snap(), initialAlpha = 1f)
fun snapOut() = fadeOut(animationSpec = snap(), targetAlpha = 1f)
fun instantTransform() = snapIn() togetherWith snapOut()

@Composable
private fun rememberNavBackStackNavEntryDecorator(backStack: NavBackStack<Screen>) = remember {
    NavEntryDecorator<Screen> { navEntry ->
        CompositionLocalProvider(
            value = LocalBackStack provides backStack,
            content = navEntry::Content,
        )
    }
}
