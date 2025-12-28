package dev.mazharhussain.smartsms.di

import dev.mazharhussain.smartsms.SmartSmsApp
import dev.mazharhussain.smartsms.requireApp
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf<SmartSmsApp>(constructor = ::requireApp)
    single<CoroutineScope> { get<SmartSmsApp>().coroutineScope }
}
