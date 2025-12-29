package dev.mazharhussain.smartsms.data

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import dev.mazharhussain.smartsms.extensions.getLong
import dev.mazharhussain.smartsms.extensions.getString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SmsRepository(private val context: Context) {
    suspend fun getThreads() = readSms(Telephony.Threads.CONTENT_URI, sortOrder = "${Column.DATE} DESC")
    suspend fun getMessages(threadId: Long) = readSms(Telephony.Sms.CONTENT_URI, selection = "${Column.THREAD_ID} = $threadId")

    private suspend fun readSms(uri: Uri, selection: String? = null, sortOrder: String? = null) = withContext(context = Dispatchers.IO) {
        val contentResolver = requireNotNull(context.contentResolver)
        val cursor = requireNotNull(contentResolver.query(uri, Column.projection, selection, null, sortOrder))
        cursor.readAll()
    }

    private fun Cursor.readAll(): List<Message> = use {
        buildList(capacity = it.count) {
            while (it.moveToNext()) {
                this += it.message
            }
        }
    }
}

private object Column {
    const val THREAD_ID = "thread_id"
    const val ADDRESS = "address"
    const val BODY = "body"
    const val DATE = "date"

    val projection = arrayOf(THREAD_ID, ADDRESS, BODY, DATE)
}

private val Cursor.threadId get() = getLong(columnName = Column.THREAD_ID)
private val Cursor.address get() = getString(columnName = Column.ADDRESS)
private val Cursor.body get() = getString(columnName = Column.BODY)
private val Cursor.date get() = getLong(columnName = Column.DATE)
private val Cursor.message get() = Message(threadId, address, body, date)
