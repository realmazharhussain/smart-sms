package dev.mazharhussain.smartsms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.mazharhussain.smartsms.ui.SsAppUi
import dev.mazharhussain.smartsms.ui.theme.AppTheme

class SsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                SsAppUi()
            }
        }
    }
}