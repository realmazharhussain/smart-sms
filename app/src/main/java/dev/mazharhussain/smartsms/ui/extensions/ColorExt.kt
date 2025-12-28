package dev.mazharhussain.smartsms.ui.extensions

import androidx.compose.ui.graphics.Color

inline fun Color.withAlpha(alpha: (Float) -> Float) = copy(alpha = alpha(this.alpha))
