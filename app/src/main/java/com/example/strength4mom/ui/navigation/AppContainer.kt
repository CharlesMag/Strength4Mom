package com.example.strength4mom.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.strength4mom.ui.theme.Strength4MomTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppContainer(
    navController: NavHostController = rememberNavController(),
    windowSize: WindowSizeClass
) {
    Strength4MomTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppTopNav(navController)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppContent(windowSize = windowSize.widthSizeClass, navController)
            }

        }
    }
}