package com.example.strength4mom

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.strength4mom.data.local.DataSourceExercises.exos
import com.example.strength4mom.ui.theme.exo.ExoScreenItem
import com.example.strength4mom.ui.theme.exo.ExoViewModel
import com.example.strength4mom.ui.theme.StartAppScreen
import com.example.strength4mom.ui.theme.search.SearchScreen
import com.example.strength4mom.ui.theme.utils.StrengthNavigationApp


@Composable
fun StrengthApp(
    viewModel: ExoViewModel = viewModel(),
    windowSize: WindowWidthSizeClass,
    navHostController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentScreen = Strength4MomScreen.valueOf(backStackEntry?.destination?.route?: Strength4MomScreen.Start.name)
    val viewModel: ExoViewModel = viewModel()

    val navigationType: StrengthNavigationApp
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = StrengthNavigationApp.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = StrengthNavigationApp.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = StrengthNavigationApp.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            navigationType = StrengthNavigationApp.BOTTOM_NAVIGATION
        }
    }

    Scaffold(
        topBar = {
            Strength4MomTopBar(
                currentScreen = currentScreen,
                canNavigate = navHostController.previousBackStackEntry != null,
                navigateUp = {navHostController.navigateUp()} ,
            )
        }
    ) { innerPadding ->
//        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navHostController,
            startDestination = Strength4MomScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Strength4MomScreen.Start.name) {
                StartAppScreen(
                    onStartWorkoutButtonClicked = { navHostController.navigate(Strength4MomScreen.ExoScreen.name) },
                    onStartSearchButtonClicked = { navHostController.navigate(Strength4MomScreen.SearchScreen.name) },
                    windowSize = WindowWidthSizeClass.Compact,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = Strength4MomScreen.ExoScreen.name) {
                // exos = listViewmodel.getData
                LazyColumn {
                    items(exos) {
                        ExoScreenItem(exo = it, windowSize)
                    }
                }
            }
            composable(route = Strength4MomScreen.SearchScreen.name) {
                    SearchScreen(modifier = Modifier)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Strength4MomTopBar(
    currentScreen: Strength4MomScreen,
    canNavigate: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            if (canNavigate) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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

enum class Strength4MomScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    ExoScreen(title = R.string.workout_page),
    SearchScreen(title = R.string.searchScreen),

}