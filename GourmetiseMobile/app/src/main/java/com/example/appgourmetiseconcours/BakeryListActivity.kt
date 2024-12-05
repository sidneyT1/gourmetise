package com.example.appgourmetiseconcours

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appgourmetiseconcours.ui.theme.AppGourmetiseConcoursTheme
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.appgourmetiseconcours.ui.theme.PurpleGrey40

class BakeryListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppGourmetiseConcoursTheme {
                val context = LocalContext.current
                val bdd = BakeryDAO(context)
                var lesBakeries = bdd.getAllBakeries()

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
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Liste des participants",
                                style = MaterialTheme.typography.h6.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            )
                            lesBakeries.forEach { bakery ->
                                BakeryRow(bakery)
                            }
                        }
                    },
                    bottomBar = {
                        BottomAppBar {
                            Button(
                                onClick = {
                                    val clientHTTP = OkHttpClient()
                                    val request = Request.Builder()
                                        .url("http://10.0.2.2:8000/api/bakery")
                                        .build()

                                    clientHTTP.newCall(request).enqueue(object : Callback {
                                        override fun onFailure(call: Call, e: IOException) {
                                            runOnUiThread {
                                                Toast.makeText(
                                                    context,
                                                    "ECHEC IMPORT ! ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                        override fun onResponse(call: Call, response: Response) {
                                            if (response.isSuccessful) {
                                                val flux = response.body?.string()
                                                Log.i("CodeHTTP", response.code.toString())
                                                Log.i("REPONSE", flux ?: "Empty Response")

                                                flux?.let {
                                                    val fluxJson = JSONArray(it)
                                                    bdd.clearAllBakeries()
                                                    for (i in 0 until fluxJson.length()) {
                                                        val jsonObject: JSONObject = fluxJson.getJSONObject(i)
                                                        bdd.insertBakery(
                                                            jsonObject.getString("siren"),
                                                            jsonObject.getString("name"),
                                                            jsonObject.getString("street"),
                                                            jsonObject.getString("postcode"),
                                                            jsonObject.getString("city"),
                                                            jsonObject.getString("phonenumber"),
                                                            jsonObject.getString("contactname"),
                                                            jsonObject.optString("description", null)
                                                        )
                                                    }
                                                    runOnUiThread {
                                                        Toast.makeText(context, "IMPORT REUSSI !", Toast.LENGTH_SHORT)
                                                            .show()
                                                    }
                                                }
                                            } else {
                                                runOnUiThread {
                                                    Toast.makeText(
                                                        context,
                                                        "ECHEC IMPORT ! ${response.code} ${response.message}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    })
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF7043))

                            ) {
                                Text("IMPORTER")

                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BakeryRow(bakery : Bakery) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ){
            Text(
                text = bakery.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = bakery.street,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = bakery.postcode,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = bakery.city,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = bakery.phonenumber,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = bakery.contactname,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = bakery.description,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

