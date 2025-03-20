package com.example.appgourmetiseconcours.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appgourmetiseconcours.UI.theme.AppGourmetiseConcoursTheme
import okhttp3.*
import org.json.JSONArray

import org.json.JSONObject
import java.io.IOException
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.appgourmetiseconcours.DAO.BakeryDAO
import com.example.appgourmetiseconcours.DAO.ContestParamsDAO
import com.example.appgourmetiseconcours.Business.ContestParams
import com.example.appgourmetiseconcours.R
import java.text.SimpleDateFormat
import java.util.*

import java.util.Date

class MainActivity : ComponentActivity() {
    private lateinit var contestParamsDAO: ContestParamsDAO
    private var contestParams by mutableStateOf<ContestParams?>(null)
    private var verifImport = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contestParamsDAO = ContestParamsDAO(this)
        getContestParams()

        setContent {
            AppGourmetiseConcoursTheme {
                Accueil(
                    VoirParticipants = {
                        if (verifImport) {
                            showToast("Données des participants déjà récupérées.")

                            startActivity(Intent(this@MainActivity, BakeryList::class.java))
                        } else {
                            getBakeries()
                        }
                    },
                    contestParams = contestParams
                )
            }
        }
    }

    private fun getContestParams() {
        try {
            val clientHTTP = OkHttpClient()
            val request = Request.Builder().url("http://10.0.2.2:8000/api/contestParams").build()

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
                                contestParamsDAO.clearAllContestParams()
                                contestParamsDAO.insertContestParams(
                                    fluxJson.getString("title"),
                                    fluxJson.getString("description"),
                                    fluxJson.getString("startRegistration"),
                                    fluxJson.getString("endRegistration"),
                                    fluxJson.getString("startEvaluation"),
                                    fluxJson.getString("endEvaluation")
                                )
                                contestParams = ContestParams(
                                    fluxJson.getString("title"),
                                    fluxJson.getString("description"),
                                    fluxJson.getString("startRegistration"),
                                    fluxJson.getString("endRegistration"),
                                    fluxJson.getString("startEvaluation"),
                                    fluxJson.getString("endEvaluation")
                                )
                                showToast("Import Concours Réussi")
                            } catch (e: Exception) {
                                showToast("Erreur parsing JSON : " + e.message)
                            }
                        }
                    } else {
                        showToast("Échec Import Concours: " + response.code + " " + response.message)
                    }
                }
            })
        } catch (e: Exception) {
            showToast("Erreur réseau: " + e.message)
        }
    }


    private fun getBakeries() {
        val clientHTTP = OkHttpClient()
        val request = Request.Builder().url("http://10.0.2.2:8000/api/bakery").build()

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
                            val jsonObject = fluxJson.getJSONObject(i)
                            bdd.insertBakery(
                                jsonObject.getString("siren"),
                                jsonObject.getString("name"),
                                jsonObject.getString("street"),
                                jsonObject.getString("postcode"),
                                jsonObject.getString("city"),
                                jsonObject.getString("phonenumber"),
                                jsonObject.getString("contactname"),
                                jsonObject.optString("description", null),
                                ticketNum = null,
                                evaluationDate = ""
                            )

                        }
                        showToast("Import Participants Réussi")
                        startActivity(Intent(this@MainActivity, BakeryList::class.java))
                        verifImport = true
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
fun Accueil(VoirParticipants: () -> Unit, contestParams: ContestParams?) {
    var showAlert by remember { mutableStateOf(false) }

    Scaffold(
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
                    contentDescription = "Logo",
                    modifier = Modifier.size(150.dp)
                )

                Text(
                    text = "Votez au grand concours pour élire la meilleure boulangerie de votre région !",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F),
                    modifier = Modifier.padding(16.dp)
                )

                contestParams?.let {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())

                    // Conversion des dates
                    val startRegDate = dateFormat.parse(it.startRegistration) ?: Date()
                    val endRegDate = dateFormat.parse(it.endRegistration) ?: Date()
                    val startEvalDate = dateFormat.parse(it.startEvaluation) ?: Date()
                    val endEvalDate = dateFormat.parse(it.endEvaluation) ?: Date()

                    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH)


                    Text(
                        text = "📌 Période d'inscription : \nDu ${dateFormatter.format(startRegDate)} au ${dateFormatter.format(endRegDate)}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )




                    Text(
                        text = "🗳️ Période d'évaluation : \nDu ${dateFormatter.format(startEvalDate)} au ${dateFormatter.format(endEvalDate)}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        val dateActuelle = Date()
                        contestParams?.let {
                            val startRegDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
                                .parse(it.startRegistration) ?: Date()
                            val endRegDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
                                .parse(it.endRegistration) ?: Date()

                            if (dateActuelle.before(startRegDate) || dateActuelle.after(endRegDate)) {
                                showAlert = true
                            } else {
                                VoirParticipants()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff2e00))
                ) {
                    Text("Voir les participants")
                }
            }
        }
    )

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text(text = "Attention") },
            text = { Text(text = "La période d'inscription est terminée ou n'a pas encore commencé.") },
            confirmButton = {
                Button(onClick = { showAlert = false }) {
                    Text("OK")
                }
            }
        )
    }
}
