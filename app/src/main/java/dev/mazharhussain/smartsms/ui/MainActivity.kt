package dev.mazharhussain.smartsms.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.provider.Telephony
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dev.mazharhussain.smartsms.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainActivityViewModel>()

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            lifecycleScope.launch(Dispatchers.Default) {
                viewModel.threads.value = getThreads()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestPermissionLauncher.launch(Manifest.permission.READ_SMS)

        setContent {
            AppTheme {
                AppUI(viewModel)
            }
        }
    }
}

fun Context.getThreads(): List<Message> {
    val threads = mutableListOf<Message>()
    val contentResolver = contentResolver ?: return threads
    val cursor = contentResolver.query(Telephony.Threads.CONTENT_URI, null, null, null, null) ?: return threads
    cursor.use {
        if (!it.moveToFirst()) {
            return@use
        }

        do {
            val message = Message(
                threadId = it.getLong(it.getColumnIndexOrThrow("thread_id")),
                address = it.getString(it.getColumnIndexOrThrow("address")),
                body = it.getString(it.getColumnIndexOrThrow("body")),
                date = it.getLong(it.getColumnIndexOrThrow("date"))
            )
            threads += message
        } while (it.moveToNext())
    }
    return threads.sortedByDescending { it.date }
}

fun Context.getMessages(threadId: Long): List<Message> {
    val messages = mutableListOf<Message>()
    val contentResolver = contentResolver ?: return messages
    val cursor = contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null) ?: return messages
    cursor.use {
        if (!it.moveToFirst()) {
            return@use
        }

        do {
            if (threadId != it.getLong(it.getColumnIndexOrThrow("thread_id"))) {
                continue
            }

            val message = Message(
                threadId = threadId,
                address = it.getString(it.getColumnIndexOrThrow("address")),
                body = it.getString(it.getColumnIndexOrThrow("body")),
                date = it.getLong(it.getColumnIndexOrThrow("date"))
            )
            messages += message
        } while (it.moveToNext())
    }
    return messages
}

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
