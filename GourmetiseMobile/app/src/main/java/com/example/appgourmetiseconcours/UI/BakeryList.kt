package com.example.appgourmetiseconcours.UI

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import com.example.appgourmetiseconcours.DAO.BakeryDAO
import com.example.appgourmetiseconcours.DAO.ContestParamsDAO
import com.example.appgourmetiseconcours.Business.ContestParams
import com.example.appgourmetiseconcours.R
import com.example.appgourmetiseconcours.UI.theme.AppGourmetiseConcoursTheme
import com.example.appgourmetiseconcours.UI.EvaluationActivity
import com.example.appgourmetiseconcours.UI.BakeryDetailActivity
import java.text.SimpleDateFormat
import java.util.*

class BakeryList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppGourmetiseConcoursTheme {
                val context = LocalContext.current
                val bdd = BakeryDAO(context)
                val contestParamsDAO = ContestParamsDAO(context)

                var lesBakeries by remember { mutableStateOf(bdd.getAllBakeries()) }
                var contestParams by remember { mutableStateOf<ContestParams?>(null) }
                val dateActuelle = Date()

                LaunchedEffect(Unit) {
                    lesBakeries = bdd.getAllBakeries()
                    contestParams = contestParamsDAO.getContestParams()
                }

                Column(modifier = Modifier.fillMaxSize()) {

                    Text(
                        text = "Liste des Boulangeries Participantes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(lesBakeries) { bakery ->

                            val isEvaluated = bdd.isBakeryEvaluated(bakery.siren)
                            val score = bdd.getBakeryScore(bakery.siren)

                            // Vérification des dates d'évaluation
                            val evaluationActive = contestParams?.let {
                                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
                                val startEvalDate = dateFormat.parse(it.startEvaluation) ?: Date()
                                val endEvalDate = dateFormat.parse(it.endEvaluation) ?: Date()
                                dateActuelle in startEvalDate..endEvalDate
                            } ?: false

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        val intent = Intent(context, BakeryDetailActivity::class.java)
                                        intent.putExtra("name", bakery.name)
                                        intent.putExtra("address", "${bakery.street}, ${bakery.postcode} ${bakery.city}")
                                        intent.putExtra("details", "Détails supplémentaires de la boulangerie")
                                        context.startActivity(intent)
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logogourmetise),
                                        contentDescription = "Logo Gourmetise",
                                        modifier = Modifier
                                            .size(64.dp)
                                            .padding(end = 16.dp)
                                    )
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = bakery.name,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                        Text(
                                            text = bakery.street + ", " + bakery.postcode + " " + bakery.city,
                                            fontSize = 17.sp,
                                        )
                                        if (isEvaluated) {
                                            Text(
                                                text = "Score: $score/15",
                                                fontSize = 14.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }

                                    Button(
                                        onClick = {
                                            if (evaluationActive && !isEvaluated) {
                                                val intent = Intent(context, EvaluationActivity::class.java)
                                                intent.putExtra("bakery_siren", bakery.siren)
                                                intent.putExtra("ticket_num", bakery.ticketNum ?: "")
                                                context.startActivity(intent)
                                            }
                                        },
                                        enabled = evaluationActive && !isEvaluated
                                    ) {
                                        Text(text = when {
                                            !evaluationActive -> "Hors période"
                                            isEvaluated -> "Évaluée"
                                            else -> "Évaluer"
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
