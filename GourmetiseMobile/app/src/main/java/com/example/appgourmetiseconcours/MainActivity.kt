package com.example.appgourmetiseconcours

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity() {
    private lateinit var contestParamsDAO: ContestParamsDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contestParamsDAO = ContestParamsDAO(this)

        fetchContestParams()

        setContent {
            AppGourmetiseConcoursTheme {
                Accueil(
                    VoirParticipants = {
                        getBakeries()
                    }
                )
            }
        }
    }

    private fun fetchContestParams() {
        val clientHTTP = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/api/contestParams")
            .build()

        clientHTTP.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                showToast("Échec Import Concours: " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val flux = response.body?.string()

                    flux?.let {
                        try {
                            val fluxJson = JSONObject(it)

                            val title = fluxJson.getString("title")
                            val description = fluxJson.getString("description")
                            val startRegistration = fluxJson.getString("startRegistration")
                            val endRegistration = fluxJson.getString("endRegistration")
                            val startEvaluation = fluxJson.getString("startEvaluation")
                            val endEvaluation = fluxJson.getString("endEvaluation")

                            contestParamsDAO.clearAllContestParams()
                            contestParamsDAO.insertContestParams(
                                title, description, startRegistration, endRegistration, startEvaluation, endEvaluation
                            )

                            showToast("Import Concours Réussi")
                        } catch (e: JSONException) {
                            showToast("Erreur parsing JSON : " + e.message)
                        }
                    }
                } else {
                    showToast("Échec Import Concours: " + response.code + " " + response.message)
                }
            }
        })
    }





    private fun getBakeries() {
        val clientHTTP = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/api/bakery")
            .build()

        clientHTTP.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                showToast("Échec Import Participants: " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val flux = response.body?.string()

                    flux?.let {
                        val fluxJson = JSONArray(it)
                        val bdd = BakeryDAO(this@MainActivity)
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

                        showToast("Import Participants Réussi")
                        startActivity(Intent(this@MainActivity, BakeryList::class.java))
                    }
                } else {
                    showToast("Échec Import Participants: " + response.code + " " + response.message)
                }
            }
        })
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Accueil(VoirParticipants: () -> Unit) {
    Scaffold(
        topBar = {},
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
                        onClick = { VoirParticipants() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff2e00))
                    ) {
                        Text("Voir les participants")
                    }
                }
            }
        }
    )
}
