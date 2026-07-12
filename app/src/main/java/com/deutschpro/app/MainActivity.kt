package com.deutschpro.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.deutschpro.app.ui.navigation.DeutschProNavGraph
import com.deutschpro.app.ui.theme.DeutschProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as DeutschProApplication

        setContent {
            val systemDark = isSystemInDarkTheme()
            val themeMode by app.preferencesManager.themeMode.collectAsState(initial = "SYSTEM")
            val useDark = when (themeMode) {
                "LIGHT" -> false
                "DARK" -> true
                else -> systemDark
            }

            DeutschProTheme(useDarkTheme = useDark) {
                DeutschProNavGraph()
            }
        }
    }
}
