package com.example.strength4mom.ui.screens

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strength4mom.data.dto.Exercise
import com.example.strength4mom.ui.viewmodels.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselScreen(
    searchViewModel: SearchViewModel = viewModel(),
    exercises: List<Exercise> = searchViewModel.exercises.value,
) {
    //SEARCH BIT
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Button(
            onClick = {
                searchViewModel.fetchExercises(
                    name = "Chest",
                )
            },
        ) {
            Text(text = "Search")
        }

        //CAROUSEL DOWN BIT
        if (exercises.isNotEmpty()) {

            ExerciseCarousel(exercises)
        } else {
            Text("No exercises available.")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseCarousel(exercises: List<Exercise>) {
    val state: PagerState = rememberPagerState { exercises.size }
    HorizontalPager(
        state = state,
        beyondViewportPageCount = 1,
        verticalAlignment = Alignment.CenterVertically,
        pageSpacing = 8.dp,
        snapPosition = SnapPosition.Start,
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(vertical = 16.dp),

        ) { index ->
        val exercise = exercises[index]
        Card(
            modifier = Modifier
                .height(500.dp)
                .width(300.dp)
                .padding(4.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Name: ${exercise.name}")
                Text("Type: ${exercise.type}")
                Text("Muscle: ${exercise.muscle}")
                Text("Equipment: ${exercise.equipment}")
                Text("Difficulty: ${exercise.difficulty}")
                Text(
                    "Instructions: ${exercise.instructions}",
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
