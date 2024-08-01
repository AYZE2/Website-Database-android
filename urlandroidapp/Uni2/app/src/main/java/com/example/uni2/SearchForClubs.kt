package com.example.uni2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.uni2.DATA.Appdatabase
import com.example.uni2.DATA.ClubsDOA
import com.example.uni2.ui.theme.Uni2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class SearchForClubs : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClubSearchScreen1()
        }
    }
}


@Composable
fun ClubSearchScreen1() {
    var searchDisplay by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter Club or League Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            scope.launch {
                searchDisplay = searchClubsInDatabase(searchQuery)
            }
        }) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = searchDisplay,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}

suspend fun searchClubsInDatabase(query: String): String {
    val clubs = withContext(Dispatchers.IO) {
        clubsDOA.searchClubs(query)
    }
    return clubs.joinToString(separator = "\n") { "${it.strTeam} (${it.strLeague}) - Stadium: ${it.strStadium}" }
}


@Preview
@Composable
fun GreetingPreview2() {
    ClubSearchScreen1()

}

