package dev.mazharhussain.smartsms.data

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
                date = 2023
            ))
        }
    }
}
