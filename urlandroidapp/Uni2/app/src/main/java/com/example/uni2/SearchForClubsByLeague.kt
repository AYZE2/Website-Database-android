package com.example.uni2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.uni2.DATA.Appdatabase
import com.example.uni2.DATA.Clubs
import com.example.uni2.DATA.ClubsDOA
import com.example.uni2.DATA.Leagues
import com.example.uni2.DATA.LeaguesDOA
import com.example.uni2.ui.theme.Uni2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class SearchForClubsByLeague : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClubSearchScreen()
        }
    }
}

suspend fun retrieveData1(clubsDOA: ClubsDOA): String {
    var allUsers = ""
// read the data
    val users: List<Clubs> = clubsDOA.getAll()
    for (u in users)
        allUsers += "${u.strTeam} ${u.strStadium}\n"
    return allUsers
}

@Composable
fun ClubSearchScreen() {
    var clubsDisplay by remember { mutableStateOf(" ") }
    var leagueName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = leagueName,
                onValueChange = { leagueName = it },
                label = { Text("Enter League Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Button(onClick = {
                    scope.launch {
                        clubsDisplay = fetchClubsByLeague(leagueName)
                    }
                }) {
                    Text("Retrieve Clubs")
                }
                Button(onClick = {
                    scope.launch {
                        val clubs = fetchClubsByLeagueList(leagueName)
                        clubsDOA.insertAll(clubs)
                        clubsDisplay = "Saved ${clubs.size} clubs to DB"
                        clubsDisplay = retrieveData1(clubsDOA)
                    }

                }) {
                    Text("save to db")
                }
                Button(onClick = {
                    scope.launch {
                        clubsDOA.insertUser(Clubs (strTeam = "$", strLeague = "Butterfly", strStadium = "lol", strDescriptionEN = "ds"))
                        clubsDisplay = retrieveData1(clubsDOA)
                    }
                }) {
                    Text("Insert User")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = clubsDisplay,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}

suspend fun fetchClubsByLeague(league: String): String {
    ///val urlstring4="https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=$league"
    val urlString = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=$league"
    val url = URL(urlString)
    val con: HttpURLConnection = url.openConnection() as HttpURLConnection
    val response = StringBuilder()

    withContext(Dispatchers.IO) {
        val reader = BufferedReader(InputStreamReader(con.inputStream))
        var line: String? = reader.readLine()
        while (line != null) {
            response.append(line + "\n")
            line = reader.readLine()
        }
    }

    return parseClubsJSON(response)
}

suspend fun fetchClubsByLeagueList(league: String): List<Clubs> {
    val urlString = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=$league"
    val url = URL(urlString)
    val con: HttpURLConnection = url.openConnection() as HttpURLConnection
    val response = StringBuilder()

    withContext(Dispatchers.IO) {
        val reader = BufferedReader(InputStreamReader(con.inputStream))
        var line: String? = reader.readLine()
        while (line != null) {
            response.append(line).append("\n")
            line = reader.readLine()
        }
        reader.close()
    }

    return parseClubsJSONToList(response)
}

fun parseClubsJSON(response: StringBuilder): String {
    val json = JSONObject(response.toString())
    val allClubs = StringBuilder()
    val jsonArray: JSONArray = json.getJSONArray("teams")

    for (i in 0 until jsonArray.length()) {
        val team: JSONObject = jsonArray.getJSONObject(i)
        val teamName = team.getString("strTeam")
        val teamStadium = team.getString("strStadium")
        val teamDescription = team.getString("strDescriptionEN")

        allClubs.append("${i + 1}) Team: $teamName\n")
        allClubs.append("   Stadium: $teamStadium\n")
        allClubs.append("   Description: $teamDescription\n\n")
    }

    return allClubs.toString()
}

fun parseClubsJSONToList(response: StringBuilder): List<Clubs> {
    val json = JSONObject(response.toString())
    val clubsList = mutableListOf<Clubs>()
    val jsonArray: JSONArray = json.getJSONArray("teams")

    for (i in 0 until jsonArray.length()) {
        val team: JSONObject = jsonArray.getJSONObject(i)
        val teamName = team.getString("strTeam")
        val teamStadium = team.getString("strStadium")
        val teamDescription = team.getString("strDescriptionEN")
        val leagueName = team.getString("strLeague")

        clubsList.add(Clubs(strTeam = teamName, strStadium = teamStadium, strDescriptionEN = teamDescription, strLeague = leagueName))
    }

    return clubsList
}

@Preview
@Composable
fun GreetingPreview() {
    ClubSearchScreen()

}