package com.example.strength4mom.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strength4mom.R
import com.example.strength4mom.data.dto.ExerciseResponse
import com.example.strength4mom.data.local.muscleList
import com.example.strength4mom.data.local.typeList
import com.example.strength4mom.ui.uistate.SearchPageUiState
import com.example.strength4mom.ui.viewmodels.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = koinViewModel(),
) {
    val searchUiState by searchViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBarItem(
            searchViewModel,
            searchUiState
        )

        FiltersAndSearch(
            searchViewModel,
            searchUiState
        )

        SearchResult(
            searchUiState
        )
    }

}

@Composable
fun SearchResult(
    searchUiState: SearchPageUiState,
    modifier: Modifier = Modifier
) {

    val isLoading = searchUiState.isLoading
    val exercises = searchUiState.exercises
    val error = searchUiState.errorMessage

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
    ) {
        // Display exercises list or a fallback message if no data
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(16.dp)
                )
            }

            error != null -> {
                Text(text = "Error: $error", color = Color.Red)
            }

            exercises.isEmpty() -> {
                Text(text = "Enter an exercise name of use the filters")
            }

            else -> {
                LazyColumn {
                    items(exercises) { exercises ->
                        ExerciseCard(exercises)

                    }
                }
            }
        }
    }
}


@Composable
fun ExerciseCard(exercise: ExerciseResponse) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = """
                                        |Name: ${exercise.name},
                                        |Muscle: ${exercise.muscle},
                                        |Type: ${exercise.type},
                                        |Equipment: ${exercise.equipment},
                                        |Instructions: ${exercise.instructions},
                                       """.trimMargin(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
fun FiltersAndSearch(
    searchViewModel: SearchViewModel = koinViewModel(),
    searchUiState: SearchPageUiState
) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
        DropdownMenu(
            expanded = searchUiState.muscleExpanded,
            onDismissRequest = { searchViewModel.muscleDropdownExpanded() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            muscleList.forEach { muscleList ->
                DropdownMenuItem(
                    text = { Text(muscleList, maxLines = 1) },
                    onClick = {
                        searchViewModel.muscleDropdownExpanded()
                        searchViewModel.muscleDropdownSelection(muscleList.lowercase())
                    }
                )
            }
        }

        DropdownMenu(
            expanded = searchUiState.typeExpanded,
            onDismissRequest = { searchViewModel.typeDropdownExpanded() },
            modifier = Modifier.fillMaxWidth()
        ) {
            typeList.forEach { typeList ->
                DropdownMenuItem(
                    text = { Text(typeList) },
                    onClick = {
                        searchViewModel.typeDropdownExpanded()
                        searchViewModel.typeDropdownSelection(typeList.lowercase())
                    }
                )
            }
        }

        Button(
            onClick = { searchViewModel.muscleDropdownExpanded() }
        ) {
            Text(
                text = searchUiState.muscleDropdownSelection?.let { "$it ▼" }
                    ?: "Muscle ▼", maxLines = 1,
                modifier = Modifier
                    .clickable { searchViewModel.muscleDropdownExpanded() }
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = { searchViewModel.typeDropdownExpanded() }
        ) {
            Text(
                text = searchUiState.typeDropdownSelection?.let { "$it ▼" } ?: "Type ▼",
                maxLines = 1,
                modifier = Modifier
                    .clickable { searchViewModel.typeDropdownExpanded() }
                    .padding(8.dp)
            )
        }

    }

    Button(
        onClick = {
            searchViewModel.loadExerciseList(
                muscle = searchUiState.muscleDropdownSelection,
                name = searchUiState.searchBarQuery,
                type = searchUiState.typeDropdownSelection
            )
        },
    ) {
        Text(text = "Search")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchBarItem(
    searchViewModel: SearchViewModel,
    searchUiState: SearchPageUiState,
    modifier: Modifier = Modifier,
) {

    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier.padding(bottom = 8.dp))

        TextField(
            value = searchUiState.searchBarQuery ?: "",
            onValueChange = { it -> searchViewModel.searchBarQueryChange(it) },
            label = { Text("Search for an exercise name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier.padding(bottom = 8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
        }
    }

}