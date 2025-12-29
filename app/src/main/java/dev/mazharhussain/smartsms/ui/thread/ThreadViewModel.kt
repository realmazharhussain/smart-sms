package dev.mazharhussain.smartsms.ui.thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mazharhussain.smartsms.data.SmsRepository
import dev.mazharhussain.smartsms.ui.Screen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

class ThreadViewModel(
    private val smsRepository: SmsRepository,
    private val params: Screen.Thread,
) : ViewModel() {
    val messages = flow { emit(smsRepository.getMessages(params.threadId)) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeout = 5.seconds),
        initialValue = emptyList()
    )
}
