package com.example.winterfellchronicles.screen.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterInfo(
    navigator: DestinationsNavigator,
    id: Int,
    viewModel: InfoViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.getCharactersInfo(id)
    }

    val characterInfoUiState = viewModel.characterInfoUiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Character Detail")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (characterInfoUiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            if (!characterInfoUiState.isLoading &&
                characterInfoUiState.error == null &&
                characterInfoUiState.data != null
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    AsyncImage(
                        model = characterInfoUiState.data.image,
                        contentDescription = null,
                    )
                    Text(text = characterInfoUiState.data.fullName)
                    Text(text = "Id : ${characterInfoUiState.data.id}")
                    Text(text = "First Name : ${characterInfoUiState.data.firstName}")
                    Text(text = "Last Name : ${characterInfoUiState.data.lastName}")
                    Text(text = "Title : ${characterInfoUiState.data.title}")
                    Text(text = "Family : ${characterInfoUiState.data.family}")
                    Text(text = "Image : ${characterInfoUiState.data.image}")
                    Text(text = "Image URL : ${characterInfoUiState.data.imageUrl}")
                }
            }
            if (
                !characterInfoUiState.isLoading &&
                characterInfoUiState.error != null
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = characterInfoUiState.error,
                )
            }
        }
    }
}
