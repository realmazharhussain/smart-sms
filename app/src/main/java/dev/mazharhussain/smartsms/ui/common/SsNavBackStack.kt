package dev.mazharhussain.smartsms.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.mazharhussain.smartsms.ui.Screen

/** The navigation back stack of SmartSms app */
typealias SsNavBackStack = NavBackStack<Screen>

/**
 * Remove the last element from the back stack, only if it is the expected [screen]
 *
 * This can be used in `onClick` callbacks of UI elements instead of [removeLastOrNull], to prevent
 * multiple quick clicks from triggering multiple pops.
 */
fun SsNavBackStack.pop(screen: Screen) = if (lastOrNull() == screen) removeLastOrNull() else null

/**
 * Add the [screen] to the end of the back stack, only if it was not already added
 *
 * This can be used in `onClick` callbacks of UI elements instead of [add][NavBackStack.add], to
 * prevent multiple quick clicks from triggering multiple pushes.
 */
fun SsNavBackStack.push(screen: Screen) = if (lastOrNull() != screen) add(screen) else false

/** Just like [rememberNavBackStack] but remembers [SsNavBackStack] instead of [NavBackStack]<[NavKey]> */
@Composable
fun rememberSsNavBackStack(initialScreen: Screen): SsNavBackStack {
    return rememberSsNavBackStack(initialScreens = arrayOf(initialScreen))
}

/** Just like [rememberNavBackStack] but remembers [SsNavBackStack] instead of [NavBackStack]<[NavKey]> */
@Composable
fun rememberSsNavBackStack(vararg initialScreens: Screen): SsNavBackStack {
    @Suppress("UNCHECKED_CAST")
    return rememberNavBackStack(elements = initialScreens) as SsNavBackStack
}

/**
 * A [CompositionLocal] of [SsNavBackStack]
 * @see [rememberNavBackStackNavEntryDecorator]
 */
val LocalBackStack = staticCompositionLocalOf { SsNavBackStack() }

/** A [NavEntryDecorator] that provides [LocalBackStack] to all [NavEntry]s in a [NavDisplay] */
@Composable
fun rememberNavBackStackNavEntryDecorator(backStack: SsNavBackStack) = remember {
    NavEntryDecorator<Screen> { navEntry ->
        CompositionLocalProvider(
            value = LocalBackStack provides backStack,
            content = navEntry::Content,
        )
    }
}
