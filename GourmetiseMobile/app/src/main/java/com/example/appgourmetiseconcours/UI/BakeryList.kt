// BakeryList.kt
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
import com.example.appgourmetiseconcours.DAO.BakeryDAO
import com.example.appgourmetiseconcours.R
import com.example.appgourmetiseconcours.UI.theme.AppGourmetiseConcoursTheme
import kotlin.random.Random




class BakeryList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppGourmetiseConcoursTheme {
                val context = LocalContext.current
                val bdd = BakeryDAO(context)
                val lesBakeries = bdd.getAllBakeries()

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

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        val ticketNum = generateTicketCode()
                                        bdd.updateTicketNum(bakery.siren, ticketNum)

                                        val intent = Intent(context, EvaluationActivity::class.java)
                                        intent.putExtra("bakery_siren", bakery.siren)
                                        intent.putExtra("ticket_num", ticketNum)
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
                                    }

                                    Button(
                                        onClick = {
                                            val ticketNum = generateTicketCode()
                                            bdd.updateTicketNum(bakery.siren, ticketNum)
                                            val intent = Intent(context, EvaluationActivity::class.java)
                                            intent.putExtra("bakery_siren", bakery.siren)
                                            intent.putExtra("ticket_num", ticketNum)
                                            context.startActivity(intent)
                                        }
                                    ) {
                                        Text(text = "Évaluer")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun generateTicketCode(): String {
        val prefix = "A"
        val randomNum = Random.nextInt(1000, 9999)
        return "$prefix$randomNum"
    }
}
