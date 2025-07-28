package com.example.strength4mom.ui.theme.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strength4mom.R
import com.example.strength4mom.network.Exercise


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
    exerciseViewModel: ExerciseViewModel = viewModel(),
    exercises: List<Exercise> = exerciseViewModel.exercises.value,
    modifier: Modifier = Modifier,
) {
    var nameQuery: String? by remember { mutableStateOf(null) }
    var muscleQuery: String? by remember { mutableStateOf(null) }
    var typeQuery: String? by remember { mutableStateOf(null) }
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }


    Column(
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DropdownMenu(
            expanded = expanded1,
            onDismissRequest = { expanded1 = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            muscleList.forEach { muscleList ->
                DropdownMenuItem(
                    text = { Text(muscleList) },
                    onClick = {
                        expanded1 = false
                        muscleQuery = muscleList.lowercase()
                    }
                )
            }
        }

        DropdownMenu(
            expanded = expanded2,
            onDismissRequest = { expanded2 = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            typeList.forEach { typeList ->
                DropdownMenuItem(
                    text = { Text(typeList) },
                    onClick = {
                        expanded2 = false
                        typeQuery = typeList.lowercase()
                    }
                )
            }
        }

        Spacer(modifier.padding(bottom = 8.dp))

        TextField(
            value = nameQuery ?: "",
            onValueChange = { query -> nameQuery = query },
            label = { Text("Search for an exercise name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
//            trailingIcon = {
//                Text(
//                    text = muscleQuery?.let { "Muscle: $it ▼" } ?: "Muscle ▼",
//                    modifier = Modifier
//                        .clickable { expanded1 = true }
//                        .padding(8.dp)
//                )
//            }
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
                onClick = { expanded1 = true}
            ) {
                Text(
                    text = muscleQuery?.let { "Muscle: $it ▼" } ?: "Muscle ▼",
                    modifier = Modifier
                        .clickable { expanded1 = true }
                        .padding(8.dp)
                )
            }

            Button(
                onClick = { expanded2 = true}
            ) {
                Text(
                    text = typeQuery?.let { "Type: $it ▼" } ?: "Type ▼",
                    modifier = Modifier
                        .clickable { expanded2 = true }
                        .padding(8.dp)
                )
            }
        }

        Button(
            onClick = { exerciseViewModel.fetchExercises(muscle = muscleQuery, name = nameQuery, type = typeQuery) },
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
            ) {
                // Display exercises list or a fallback message if no data
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
                    // Fallback text if no exercises are found
                    Text(text = "No exercises found")
                }
            }
        }


    }
}


@Composable
fun ScreenResult(modifier: Modifier = Modifier) {

}