package com.example.winterfellchronicles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.winterfellchronicles.screens.NavGraphs
import com.example.winterfellchronicles.ui.theme.WinterFellChroniclesTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WinterFellChroniclesTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}