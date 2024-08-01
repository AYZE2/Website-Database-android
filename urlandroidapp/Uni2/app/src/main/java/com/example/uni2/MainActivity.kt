package com.example.uni2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.uni2.DATA.Appdatabase
import com.example.uni2.DATA.ClubsDOA
import com.example.uni2.DATA.Leagues
import com.example.uni2.DATA.LeaguesDOA
import kotlinx.coroutines.launch

lateinit var db1: Appdatabase
lateinit var clubsDOA: ClubsDOA


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db1 = Room.databaseBuilder(this,
            Appdatabase::class.java,"club").build()
        clubsDOA=db1.ClubsDOA()
        setContent {
            displayGUI(displayMSG = "hey")
            ///GUI4()

        }
    }
}

@Composable
fun displayGUI(displayMSG: String){

        Column {
        val context = LocalContext.current
        Text(displayMSG)
        Button(onClick= {
            val i = Intent(context, AddLeaguestoBD::class.java)
            context.startActivity(i)
        })
        {
            Text(text = "Add Leagues to DB")
        }
        Button(onClick= {
            val i = Intent(context, SearchForClubsByLeague::class.java)
            context.startActivity(i)
        })
        {
            Text(text = "Search for Clubs By Leagues")
        }
        Button(onClick= {
            val i = Intent(context, SearchForClubs::class.java)
            context.startActivity(i)
        })
        {
            Text(text = "Search For Clubs")
        }

    }

}

@Preview
@Composable
fun displayGUIPreview(){
    displayGUI(displayMSG ="hi" )
    ///GUI4()
}

