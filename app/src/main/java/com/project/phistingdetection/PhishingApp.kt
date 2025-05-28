package com.project.phistingdetection

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.phistingdetection.MainScreen.MainScreen

@Composable
fun PhishingApp(
    modifier: Modifier = Modifier,
    initialUrl: String = ""
) {
    MainScreen(initialUrl = initialUrl)
}