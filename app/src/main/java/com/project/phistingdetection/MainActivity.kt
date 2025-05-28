package com.project.phistingdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.phistingdetection.MainScreen.MainScreen
import com.project.phistingdetection.ui.theme.PhistingDetectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val incomingUrl = intent?.data?.toString() ?: ""

        enableEdgeToEdge()
        setContent {
            PhistingDetectionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PhishingApp(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        initialUrl = incomingUrl
                    )
                }
            }
        }
    }
}