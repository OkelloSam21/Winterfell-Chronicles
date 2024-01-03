package com.example.winterfellchronicles.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.winterfellchronicles.data.remote.RetrofitInstance
import com.example.winterfellchronicles.data.remote.WinterFellResponse
import com.example.winterfellchronicles.screen.destinations.CharacterInfoDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    navigator: DestinationsNavigator,
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Winter-fell Chronicles") })
    }) { paddingValues ->
        val context = LocalContext.current
        var winterFellResponse by remember {
            mutableStateOf<List<WinterFellResponse>?>(null)
        }

        RetrofitInstance.api.getCharacters().enqueue(object : Callback<List<WinterFellResponse>> {
            override fun onResponse(
                call: Call<List<WinterFellResponse>>,
                response: Response<List<WinterFellResponse>>,
            ) {
                Toast.makeText(context, "API call was successful", Toast.LENGTH_SHORT).show()
                winterFellResponse = response.body()
            }

            override fun onFailure(call: Call<List<WinterFellResponse>>, t: Throwable) {
                Toast.makeText(context, "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
        if (!winterFellResponse.isNullOrEmpty()) {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(winterFellResponse!!) { winterfell ->
                    CharactersCard(winterFellResponse = winterfell, onClick = {
                        navigator.navigate(CharacterInfoDestination(id = it))
                    })
                }
            }
        } else {
            CircularProgressIndicator()
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
