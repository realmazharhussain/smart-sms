package dev.mazharhussain.smartsms.ui.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mazharhussain.smartsms.data.SmsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

class ConversationsViewModel(
    private val smsRepository: SmsRepository,
) : ViewModel() {
    val threads = flow { emit(smsRepository.getThreads()) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeout = 5.seconds),
        initialValue = emptyList()
    )
}
