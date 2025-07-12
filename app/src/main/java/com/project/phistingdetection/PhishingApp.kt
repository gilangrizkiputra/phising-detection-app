package com.project.phistingdetection

import AppNavigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.project.phistingdetection.MainScreen.MainScreen

@Composable
fun PhishingApp(
    modifier: Modifier = Modifier,
    initialUrl: String = ""
) {
    val navController = rememberNavController()
    AppNavigation(navController = navController, initialUrl = initialUrl)
}