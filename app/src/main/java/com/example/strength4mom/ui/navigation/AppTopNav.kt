package com.example.strength4mom.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopNav(
    navHostController: NavHostController,
) {
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentScreen: Strength4MomScreen = Strength4MomScreen.valueOf(
        backStackEntry?.destination?.route ?: Strength4MomScreen.Start.name
    )
    val canNavigate: Boolean = navHostController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navHostController.navigateUp() }

    CenterAlignedTopAppBar(
        navigationIcon = {
            if (canNavigate) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(currentScreen.title),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        },
        modifier = Modifier
    )
}