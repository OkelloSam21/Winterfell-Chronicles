package com.example.winterfellchronicles.screen.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.winterfellchronicles.data.remote.WinterFellResponse
import com.example.winterfellchronicles.screen.destinations.CharacterInfoDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    navigator: DestinationsNavigator,
    viewModel: CharacterViewModel = hiltViewModel(),
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Characters") })
    }) { paddingValues ->
        val winterfellCharacterUiState = viewModel.winterFellCharacterUiState.collectAsState().value

        Box(modifier = Modifier.padding(paddingValues)) {
            if (winterfellCharacterUiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            } else if (
                !winterfellCharacterUiState.isLoading &&
                winterfellCharacterUiState.error == null &&
                winterfellCharacterUiState.data.isNotEmpty()
            ) {
                LazyColumn {
                    items(winterfellCharacterUiState.data.size) { character ->
                        CharactersCard(
                            winterFellResponse = winterfellCharacterUiState.data[character],
                            onClick = {
                                navigator.navigate(CharacterInfoDestination(id = character))
                            },
                        )
                    }
                }
            } else if (
                !winterfellCharacterUiState.isLoading &&
                winterfellCharacterUiState.error == null &&
                winterfellCharacterUiState.data.isEmpty()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "No Characters Found",
                )
            } else {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = winterfellCharacterUiState.error ?: "Unknown Error",
                )
            }
        }
    }
}

@Composable
fun CharactersCard(
    winterFellResponse: WinterFellResponse,
    onClick: (id: Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(75.dp)
            .clickable {
                onClick(winterFellResponse.id)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = winterFellResponse.fullName)

            AsyncImage(
                model = winterFellResponse.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape),
            )
        }
    }
}
