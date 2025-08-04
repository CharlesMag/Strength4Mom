package com.example.strength4mom.ui.theme.search

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
import com.example.strength4mom.network.Exercise
import com.example.strength4mom.ui.theme.utils.muscleList
import com.example.strength4mom.ui.theme.utils.typeList
import android.util.Log
import kotlin.math.log


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel(),
    exercises: List<Exercise> = searchViewModel.exercises.value,
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
            exercises,
            searchViewModel
        )
    }

}

@Composable
fun SearchResult(
    exercises: List<Exercise>,
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
    ) {
        val isLoading by searchViewModel.isLoading
        // Display exercises list or a fallback message if no data
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .padding(16.dp)
            )
        }
        if (!isLoading && exercises.isEmpty()) {
            Text(text = "Enter an exercise name of use the filters")
        }

        if (exercises.isNotEmpty()) {
            var itemCount = exercises.size
            println("NUMBER OF EXO: $itemCount")
        }
    }
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(8.dp)
    ) {
        if (exercises.isNotEmpty()) {
            exercises.forEach { exercise ->
                Card(
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    LazyColumn {
                        items(exercises) { exercise ->
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
                }
            }
        } else {
            val error by searchViewModel.errorMessage
            // Fallback text if no exercises are found
            if (error != null) {
                Text(text = "Error: $error", color = Color.Red)
            }
        }
    }
}

@Composable
fun FiltersAndSearch(
    searchViewModel: SearchViewModel = viewModel(),
    searchUiState: SearchUiState
) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
        DropdownMenu(
            expanded = searchUiState.muscleExpanded,
            onDismissRequest = { searchViewModel.muscleExpanded() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            muscleList.forEach { muscleList ->
                DropdownMenuItem(
                    text = { Text(muscleList, maxLines = 1) },
                    onClick = {
                        searchViewModel.muscleExpanded()
                        searchViewModel.muscleDropdownSelection(muscleList.lowercase())
                    }
                )
            }
        }

        DropdownMenu(
            expanded = searchUiState.typeExpanded,
            onDismissRequest = { searchViewModel.typeExpanded() },
            modifier = Modifier.fillMaxWidth()
        ) {
            typeList.forEach { typeList ->
                DropdownMenuItem(
                    text = { Text(typeList) },
                    onClick = {
                        searchViewModel.typeExpanded()
                        searchViewModel.typeDropdownSelection(typeList.lowercase())
                    }
                )
            }
        }

        Button(
            onClick = { searchViewModel.muscleExpanded() }
        ) {
            Text(
                text = searchUiState.muscleDropdownSelection?.let { "$it ▼" }
                    ?: "Muscle ▼", maxLines = 1,
                modifier = Modifier
                    .clickable { searchViewModel.muscleExpanded() }
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = { searchViewModel.typeExpanded() }
        ) {
            Text(
                text = searchUiState.typeDropdownSelection?.let { "$it ▼" } ?: "Type ▼", maxLines = 1,
                modifier = Modifier
                    .clickable { searchViewModel.typeExpanded() }
                    .padding(8.dp)
            )
        }

    }

    Button(
        onClick = {
            searchViewModel.fetchExercises(
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
    searchUiState: SearchUiState,
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