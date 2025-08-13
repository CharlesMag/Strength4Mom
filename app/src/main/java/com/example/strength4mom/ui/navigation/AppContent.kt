package com.example.strength4mom.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.strength4mom.R
import com.example.strength4mom.data.local.DataSourceExercises.exos
import com.example.strength4mom.ui.screens.CarouselScreen
import com.example.strength4mom.ui.screens.ExoScreenItem
import com.example.strength4mom.ui.screens.SearchScreen
import com.example.strength4mom.ui.screens.StartAppScreen

@Composable
fun AppContent(
    windowSize: WindowWidthSizeClass,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Strength4MomScreen.Start.name,
        modifier = Modifier
    ) {
        composable(route = Strength4MomScreen.Start.name) {
            StartAppScreen(
                onStartWorkoutButtonClicked = { navHostController.navigate(Strength4MomScreen.ExoScreen.name) },
                onStartSearchButtonClicked = { navHostController.navigate(Strength4MomScreen.SearchScreen.name) },
                onStartCarouselTest = { navHostController.navigate(Strength4MomScreen.CarouselScreen.name) },
                windowSize = WindowWidthSizeClass.Compact,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
        composable(route = Strength4MomScreen.ExoScreen.name) {
            LazyColumn {
                items(exos) {
                    ExoScreenItem(exo = it, windowSize)
                }
            }
        }
        composable(route = Strength4MomScreen.SearchScreen.name) {
            SearchScreen()
        }
        composable(route = Strength4MomScreen.CarouselScreen.name) {
            CarouselScreen()
        }
    }
}

enum class Strength4MomScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    ExoScreen(title = R.string.workout_page),
    SearchScreen(title = R.string.searchScreen),
    CarouselScreen(title = R.string.carouselScreen),

}