package dev.mazharhussain.smartsms.ui.common

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith

/** An instant (no animation) [EnterTransition] */
fun snapIn() = fadeIn(animationSpec = snap(), initialAlpha = 1f)
/** An instant (no animation) [ExitTransition] */
fun snapOut() = fadeOut(animationSpec = snap(), targetAlpha = 1f)
/** An instant (no animation) [ContentTransform] */
fun instantTransform() = snapIn() togetherWith snapOut()
