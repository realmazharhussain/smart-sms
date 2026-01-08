package dev.mazharhussain.smartsms.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

fun tickerFlow(duration: Duration) = flow {
    var num = 0
    while (true) {
        delay(duration)
        emit(value = ++num)
    }
}
