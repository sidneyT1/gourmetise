package com.example.appgourmetiseconcours

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appgourmetiseconcours.ui.theme.AppGourmetiseConcoursTheme

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: BakeryHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        dbHelper = BakeryHelper(this)
        dbHelper.writableDatabase

        setContent {
            AppGourmetiseConcoursTheme {
                Accueil(
                    onSeeParticipantsClicked = {
                        startActivity(
                            Intent(this, BakeryListActivity::class.java)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun Accueil(onSeeParticipantsClicked: () -> Unit) {
    Scaffold(
        topBar = {

        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logogourmetise),
                    contentDescription = "Logo du Concours",
                    modifier = Modifier.size(150.dp)
                )

                Text(
                    text = "Votez au grand concours pour élire la meilleure boulangerie de votre région !",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F),
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = "Du 18 mai 2025 au 18 juillet 2025",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F),
                    modifier = Modifier.padding(16.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onSeeParticipantsClicked() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043))
                    ) {
                        Text("Voir les participants")
                    }
                }
            }
        }
    )
}
