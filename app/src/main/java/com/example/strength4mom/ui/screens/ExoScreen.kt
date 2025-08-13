package com.example.strength4mom.ui.screens

//import com.example.strength4mom.ui.theme.previews.ExoPreviewObject
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strength4mom.R
import com.example.strength4mom.data.dto.ExoItemClass
import com.example.strength4mom.ui.viewmodels.ExoViewModel


/**
 * Composable displaying the list of exercises with the image and information
 */
@Composable
fun ExoScreenItem(
    exo: ExoItemClass.Exo,
    windowSize: WindowWidthSizeClass,
    viewModel: ExoViewModel = viewModel(),
    exoViewModel: ExoViewModel = viewModel(key = exo.Id.toString()),
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            //Making sure the whole card is clickable to expand
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable(
                onClick = { exoViewModel.updateExpanded() }
            )
    ) {
        ExoInfo(
            exoName = exo.name,
            exoSets = exo.numberSet,
            exoReps = exo.numberRep,
            exoImage = exo.imageResourceID,
            exoDescription = exo.description,
            id = exo.Id
        )
    }
    Spacer(modifier = modifier.size(12.dp))
}

//Composable to add information about the exercise on the card
@Composable
fun ExoInfo(
    @StringRes exoName: Int,
    exoSets: Int,
    exoReps: Int,
    exoImage: Int,
    exoDescription: Int,
    id: Int,
    //Generating a viewModel for each exercise to be able to have an independent expanded
    exoViewModel: ExoViewModel = viewModel(key = id.toString()),
    modifier: Modifier = Modifier
) {
    val exoUiState by exoViewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .then(
                when (exoUiState.exoDone) {
                    true -> {
                        Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    }

                    else -> {
                        Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    }
                }
            )
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(exoName),
            style = MaterialTheme.typography.displayMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.reps, exoReps),
                style = MaterialTheme.typography.displaySmall,
                modifier = modifier
            )
            Button(
                onClick = { exoViewModel.triageCurrentSet(exoSets) },
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(R.string.sets, exoUiState.currentSet, exoSets)
                )
            }
            ExoButton(
                expanded = exoUiState.expanded,
                onClick = { exoViewModel.updateExpanded() }
            )
        }

        if (exoUiState.currentSetGhost > exoSets) {
            AlertDialog(
                onDismissRequest = { exoViewModel.closeOpenDialog() },
                confirmButton = {
                    TextButton(
                        onClick = {
                            exoViewModel.resetCurrentSet()
                            exoViewModel.updateExoCapsule()
                        }
                    ) {
                        Text(stringResource(R.string.reset_button))
                    }
                },
                title = {
                    Text(stringResource(R.string.reset_title))
                },
                text = {
                    Text(stringResource(R.string.reset_body))
                },
                dismissButton = {
                    TextButton(
                        onClick = { exoViewModel.closeOpenDialog() }
                    ) {
                        Text(stringResource(R.string.reset_cancel))
                    }
                },
            )
        }

        if (exoUiState.expanded) {
            ExoExpanded(
                image = exoImage,
                exoDescription = exoDescription,
                modifier = Modifier
            )
        }
    }
}

//Icon for expandMore or Less for the exercise displaying additional info if expend = true
//Added Gradle dependency to be able to use icons from https://fonts.google.com/icons
//Added implementation("androidx.compose.material:material-icons-extended") to to :app build.gradle.kts file
@Composable
private fun ExoButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (!expanded) Icons.Filled.ExpandMore else Icons.Filled.ExpandLess,
            contentDescription = stringResource(R.string.expand_button),
        )
    }
}

@Composable
fun ExoExpanded(
    image: Int,
    exoDescription: Int,
    modifier: Modifier
) {
    Image(
        painterResource(image),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small)
    )
    Text(stringResource(exoDescription))
}
