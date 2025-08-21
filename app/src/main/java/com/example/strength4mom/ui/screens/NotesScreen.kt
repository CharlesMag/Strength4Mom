package com.example.strength4mom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NotesScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Text(text = "Find my notes below")
        inputScreen()

    }
}

@Composable
fun inputScreen() {
    Row {
        Text("How was your training yesterday?")
        TextField(
            value = ,// NEED UISTATE
        )
    }
}