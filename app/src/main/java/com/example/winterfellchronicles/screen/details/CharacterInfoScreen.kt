package com.example.winterfellchronicles.screen.details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
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

    val characterInfoUiState by viewModel.characterInfoUiState.collectAsState()

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
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            if (characterInfoUiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                )
            }

            if (!characterInfoUiState.isLoading &&
                characterInfoUiState.error == null
            ) {
                Log.d("info","CharacterInfoScreen: ${characterInfoUiState.data}")
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                ) {
                    AsyncImage(
                        model = characterInfoUiState.data?.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp,40.dp)
                    )
                    characterInfoUiState.data?.let { Text(text = it.fullName) }
                    Text(text = "Id : ${characterInfoUiState.data?.id}")
                    Text(text = "First Name : ${characterInfoUiState.data?.firstName}")
                    Text(text = "Last Name : ${characterInfoUiState.data?.lastName}")
                    Text(text = "Title : ${characterInfoUiState.data?.title}")
                    Text(text = "Family : ${characterInfoUiState.data?.family}")
                    Text(text = "Image : ${characterInfoUiState.data?.image}")
                    Text(text = "Image URL : ${characterInfoUiState.data?.imageUrl}")
                }
            }
            if (
                !characterInfoUiState.isLoading &&
                characterInfoUiState.error != null
            ) {
                Text(
                    modifier =
                    Modifier.align(Alignment.Center),
                    text = characterInfoUiState.error!!,
                )
            }
        }
    }
}
