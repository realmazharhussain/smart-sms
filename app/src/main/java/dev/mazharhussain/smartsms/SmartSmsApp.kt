package dev.mazharhussain.smartsms

import android.app.Application
import java.lang.ref.WeakReference

class SmartSmsApp : Application() {
    init { appRef = WeakReference(this) }
}

var appRef: WeakReference<SmartSmsApp>? = null
    private set

fun requireApp(): SmartSmsApp {
    val ref = requireNotNull(appRef) { "App not initialized yet!" }
    val app = requireNotNull(ref.get()) { "App already destroyed!" }
    return app
}