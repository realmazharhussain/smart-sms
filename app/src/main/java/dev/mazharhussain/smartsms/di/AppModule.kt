package dev.mazharhussain.smartsms.di

import dev.mazharhussain.smartsms.SsApplication
import dev.mazharhussain.smartsms.data.SmsRepository
import dev.mazharhussain.smartsms.requireApp
import dev.mazharhussain.smartsms.ui.conversations.ConversationsViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf<SsApplication>(constructor = ::requireApp)
    single<CoroutineScope> { get<SsApplication>().coroutineScope }
    singleOf(constructor = ::SmsRepository)
    viewModelOf(constructor = ::ConversationsViewModel)
}
