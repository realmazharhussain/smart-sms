package dev.mazharhussain.smartsms

import android.app.Application
import dev.mazharhussain.smartsms.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference

class SsApplication : Application() {
    val coroutineScope = CoroutineScope(context = SupervisorJob())

    init { appRef = WeakReference(this) }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(androidContext = this@SsApplication)
            modules(modules = appModule)
        }
    }
}

var appRef: WeakReference<SsApplication>? = null
    private set

fun requireApp(): SsApplication {
    val ref = requireNotNull(appRef) { "App not initialized yet!" }
    val app = requireNotNull(ref.get()) { "App already destroyed!" }
    return app
}