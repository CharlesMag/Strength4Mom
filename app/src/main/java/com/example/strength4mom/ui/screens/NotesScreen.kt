package com.example.strength4mom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.strength4mom.data.local.notes.Notes
import com.example.strength4mom.ui.uistate.NotesScreenUiState
import com.example.strength4mom.ui.viewmodels.NotesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel = koinViewModel()
) {
    val notesScreenUiState by notesViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Text(
            text = "Find my notes below",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.size(16.dp))
        inputScreen(
            notesScreenUiState,
            notesViewModel,
            onSaveClick = {
                coroutineScope.launch {
                    notesViewModel.saveNotes()
                    notesViewModel.clearInputTextField()
                }
            }
        )
        Spacer(Modifier.size(16.dp))
        screenBody(notesScreenUiState.notesList)
    }
}

@Composable
fun inputScreen(
    notesScreenUiState: NotesScreenUiState,
    notesViewModel: NotesViewModel,
    onSaveClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("How was your training yesterday?")
        TextField(
            value = notesScreenUiState.notesBarTextField ?: "",
            onValueChange = { it -> notesViewModel.notesBarEntryChange(it) },
            label = { Text(text = "Write here") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = notesScreenUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun screenBody(
    notesList: List<Notes>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
    ) {
        if (notesList.isEmpty()) {
            Text(text = "There is nothing saved yet")
        } else {
            notesList(notesList)
        }
    }

}

@Composable
fun notesList(
    notesList: List<Notes>
) {
    LazyColumn {
        items(items = notesList, key = { it.id }) { notes ->
            Card(
                shape = CardDefaults.shape,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)

                ) {
                    Text(text = notes.notes)
                }
            }
        }
    }
}