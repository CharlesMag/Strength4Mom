package com.example.strength4mom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.findFirstRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.strength4mom.ui.uistate.NotesScreenUiState
import com.example.strength4mom.ui.viewmodels.NotesViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel = koinViewModel()
) {
    val notesScreenUiState by notesViewModel.uiState.collectAsState()

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
            notesViewModel
        )

    }
}

@Composable
fun inputScreen(
    notesScreenUiState: NotesScreenUiState,
    notesViewModel: NotesViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("How was your training yesterday?")
        TextField(
            value = notesScreenUiState.notesBarTextField ?: "",
            onValueChange = { it -> notesViewModel.notesTextFieldInput(it) },
            label = { Text(text = "Write here") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun savedNotes(no) {

}