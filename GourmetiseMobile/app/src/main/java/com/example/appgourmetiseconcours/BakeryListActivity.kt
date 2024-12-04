package com.example.appgourmetiseconcours

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appgourmetiseconcours.ui.theme.AppGourmetiseConcoursTheme

class BakeryListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppGourmetiseConcoursTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Participants") }
                        )
                    },
                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                            Text(
                                text = "Liste des participants",
                                style = MaterialTheme.typography.h6.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    },
                    bottomBar = {
                        BottomAppBar {
                            Button(
                                onClick = { },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF7043))
                            ) {
                                Text("Importer")
                            }
                        }
                    }
                )
            }
        }
    }
}
