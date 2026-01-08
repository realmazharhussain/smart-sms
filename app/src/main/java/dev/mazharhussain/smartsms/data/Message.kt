package dev.mazharhussain.smartsms.data

import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.times

data class Message(
    val threadId: Long,
    val address: String,
    val body: String,
    val date: Long,
) {
    companion object;
}

val Message.Companion.mockList by lazy {
    buildList(20) {
        repeat(20) {
            add(Message(
                threadId = it.toLong(),
                address = "Jazz4G",
                body = "Jazz Balance Saver lag gayi hai jis se base rate pe data charge nhi hoga.",
                date = Clock.System.now().run {
                    Random.nextLong(
                        from = (this - 2 * 365.days).toEpochMilliseconds(),
                        until = this.toEpochMilliseconds()
                    )
                }
            ))
        }
    }.sortedByDescending { it.date }
}
