package com.example.uni2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.uni2.DATA.Appdatabase
import com.example.uni2.DATA.Leagues
import com.example.uni2.DATA.LeaguesDOA
import com.example.uni2.ui.theme.Uni2Theme
import kotlinx.coroutines.launch

lateinit var db: Appdatabase
lateinit var leaguesDOA: LeaguesDOA

class AddLeaguestoBD : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(this,
            Appdatabase::class.java,"mydatabase").build()
        leaguesDOA=db.LeaguesDOA()
        setContent {
            Greeting3()

        }
    }
}

suspend fun retrieveData(leaguesDOA: LeaguesDOA): String {
    var allUsers = ""
// read the data
    val users: List<Leagues> = leaguesDOA.getAll()
    for (u in users)
        allUsers += "${u.strLeague} ${u.strSport}\n"
    return allUsers
}

@Composable
fun Greeting3() {

    var LeagueString by remember { mutableStateOf("") }
    LaunchedEffect(LeagueString){
        leaguesDOA.insertAll(
            Leagues(1, "4328", "English Premier League","Soccer","Premier League, EPL"),
            Leagues(2, "4329", "English League Championship","Soccer","Championship"),
            Leagues(3, "4330", "Scottish Premier League","Soccer","Scottish Premiership, SPFL"),
            Leagues(4, "4331", "German Bundesliga","Soccer","Bundesliga, Fußball-Bundesliga"),
            Leagues(5, "4332", "Italian Serie A","Soccer","Serie A"),
            Leagues(6, "4334", "French Ligue 1","Soccer","Ligue 1 Conforama"),
            Leagues(7, "4335", "Spanish La Liga","Soccer","LaLiga Santander, La Liga"),
            Leagues(8, "4336", "Greek Superleague Greece","Soccer","tes"),
            Leagues(9, "4337", "Dutch Eredivisie","Soccer","Eredivisie"),
            Leagues(10, "4338", "Belgian First Division A","Soccer","Jupiler Pro League"),
            Leagues(11, "4339", "Turkish Super Lig","Soccer","Super Lig"),
            Leagues(12, "4340", "Danish Superliga","Soccer","tes"),
            Leagues(13, "4344", "Portuguese Primeira Liga","Soccer","liga NOS"),
            Leagues(14, "4346", "American Major League Soccer","Soccer","MLS, Major League Soccer"),
            Leagues(15, "4347", "Swedish Allsvenskan","Soccer","Fotbollsallsvenskan"),
            Leagues(16, "4351", "Mexican Primera League","Soccer","Liga MX"),
            Leagues(17, "4354", "Brazilian Serie A","Soccer","tes"),
            Leagues(18, "4355", "Ukrainian Premier League","Soccer","tes"),
            Leagues(19, "4356", "Russian Football Premier League","Soccer","Чемпионат России по футболу"),
            Leagues(20, "4358", "Australian A-League","Soccer","A-League"),
            Leagues(21, "4359", "Norwegian Eliteserien","Soccer","Eliteserien"),
            Leagues(22, "4331", "Chinese Super League","Soccer","tes"),


        )
    }
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Button(onClick = {
                scope.launch {
                    LeagueString = retrieveData(leaguesDOA)
                }
            })
            {
                Text("Retrieve data")
            }
            Button(onClick = {
                scope.launch {
                    leaguesDOA.insertUser(Leagues (idLeague = "Bob", strLeague = "Butterfly", strLeagueAlternate = "lol", strSport = "ds"))
                    LeagueString = retrieveData(leaguesDOA)
                }
            }) {
                Text("Insert User")
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = LeagueString
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    Greeting3()

}