package com.example.appgourmetiseconcours

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.appgourmetiseconcours.ui.theme.AppGourmetiseConcoursTheme

class BakeryList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppGourmetiseConcoursTheme {
                val context = LocalContext.current
                val bdd = BakeryDAO(context)
                val lesBakeries = bdd.getAllBakeries()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(lesBakeries) { bakery ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    val intent = Intent(context, BakeryDetailActivity::class.java)
                                    intent.putExtra("name", bakery.name)


                                    val address = bakery.street + ", " + bakery.postcode + " " + bakery.city
                                    intent.putExtra("address", address)


                                    val details = "Téléphone: " + bakery.phonenumber + "\n" +
                                            "Contact: " + bakery.contactname + "\n" +
                                            "Description: " + (bakery.description)
                                    intent.putExtra("details", details)

                                    context.startActivity(intent)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logogourmetise),
                                    contentDescription = "Logo Gourmetise",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .padding(end = 16.dp)
                                )
                                Column {
                                    Text(
                                        text = bakery.name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Text(
                                        text = bakery.street + ", " + bakery.city,
                                        fontSize = 17.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
