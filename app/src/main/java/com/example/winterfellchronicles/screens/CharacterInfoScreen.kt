package com.example.winterfellchronicles.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.winterfellchronicles.data.remote.RetrofitInstance
import com.example.winterfellchronicles.data.remote.WinterFellResponse
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterInfo(
    navigator: DestinationsNavigator,
    id: Int
) {
    val context = LocalContext.current

    var winterFellResponse by remember(id) {
        mutableStateOf<WinterFellResponse?>(null)
    }

    RetrofitInstance.api.getCharacterById().enqueue(object :Callback<WinterFellResponse>{
        override fun onResponse(
            call: Call<WinterFellResponse>,
            response: Response<WinterFellResponse>
        ) {
            winterFellResponse = response.body()
        }

        override fun onFailure(call: Call<WinterFellResponse>, t: Throwable) {
            Toast.makeText(context,"Failed to fetch Character Info", Toast.LENGTH_SHORT).show()
        }
    })
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Character Detail") 
            },
            navigationIcon = {
                IconButton(onClick = {
                    navigator.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            }
        )
    }) { paddingValues ->
        if (winterFellResponse != null){
            val character = winterFellResponse!!
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),

                    ) {
                    Text(text = character.fullName)
                    AsyncImage(model = character.imageUrl, contentDescription = null)
                    Text(text = " ID : ${character.id}")
                    Text(text = "First Name : ${character.firstName}")
                    Text(text = "Last Name : ${character.lastName}")
                    Text(text = "Title : ${character.title}")
                    Text(text = "Family : ${character.family}")
                    Text(text = "Image : ${character.image}")
                    Text(text = "Image URL : ${character.imageUrl}")
                }
            }
        }else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
                ){
                CircularProgressIndicator()
            }
        }
    }
}

