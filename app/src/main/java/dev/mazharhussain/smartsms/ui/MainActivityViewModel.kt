package dev.mazharhussain.smartsms.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivityViewModel() : ViewModel() {
    val threads = MutableStateFlow(emptyList<Message>())
}
