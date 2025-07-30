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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        SearchBarItem()
//        ScreenResult()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchBarItem(
    searchViewModel: SearchViewModel = viewModel(),
    exercises: List<Exercise> = searchViewModel.exercises.value,
    modifier: Modifier = Modifier,
) {
    val searchUiState by searchViewModel.uiState.collectAsState()

    Column(
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DropdownMenu(
            expanded = searchUiState.muscleExpanded,
            onDismissRequest = { searchViewModel.muscleExpanded() },
            modifier = Modifier.fillMaxWidth()
        ) {
            muscleList.forEach { muscleList ->
                DropdownMenuItem(
                    text = { Text(muscleList) },
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
            Button(
                onClick = { searchViewModel.muscleExpanded() }
            ) {
                Text(
                    text = searchUiState.muscleDropdownSelection?.let { "Muscle: $it ▼" }
                        ?: "Muscle ▼",
                    modifier = Modifier
                        .clickable { searchViewModel.muscleExpanded() }
                        .padding(8.dp)
                )
            }

            Button(
                onClick = { searchViewModel.typeExpanded() }
            ) {
                Text(
                    text = searchUiState.typeDropdownSelection?.let { "Type: $it ▼" } ?: "Type ▼",
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
        Card(
            modifier = modifier
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
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
                } else {
                    Text(text = "Enter an exercise name of use the filters")
                }

                if (exercises.isNotEmpty()) {
                    exercises.forEach { exercise ->
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
                } else {
                    val error by searchViewModel.errorMessage
                    // Fallback text if no exercises are found
                    if (error != null) {
                        Text(text = "Error: $error", color = Color.Red)
                    }
                }
            }
        }


    }
}


@Composable
fun ScreenResult(modifier: Modifier = Modifier) {

}