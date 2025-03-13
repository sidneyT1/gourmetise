// EvaluationActivity.kt
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import com.example.appgourmetiseconcours.DAO.NoteDAO
import com.example.appgourmetiseconcours.DAO.CriteriaDAO
import com.example.appgourmetiseconcours.DAO.BakeryDAO
import com.example.appgourmetiseconcours.R

class EvaluationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bakerySiren = intent.getStringExtra("bakery_siren") ?: ""
        val ticketNum = intent.getStringExtra("ticket_num") ?: ""

        setContent {
            var note1 by remember { mutableStateOf("") }
            var note2 by remember { mutableStateOf("") }
            var note3 by remember { mutableStateOf("") }
            var bakeryName by remember { mutableStateOf("") }
            var ticketCode by remember { mutableStateOf("") }
            var isTicketValid by remember { mutableStateOf(false) }

            val context = LocalContext.current

            LaunchedEffect(bakerySiren) {
                if (bakerySiren.isNotEmpty()) {
                    val bakeryDAO = BakeryDAO(context)
                    bakeryName = bakeryDAO.getBakeryNameBySiren(bakerySiren)
                }
            }


            if (ticketCode == ticketNum) {
                isTicketValid = true
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Évaluation de la Boulangerie",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (bakeryName.isNotEmpty()) {
                    Text(
                        text = "Boulangerie: $bakeryName",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                } else {
                    Text(
                        text = "Nom de la boulangerie introuvable",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }


                Text("Entrez votre code ticket:")
                BasicTextField(
                    value = ticketCode,
                    onValueChange = { ticketCode = it },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()

                        .padding(16.dp)
                )


                if (isTicketValid) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Accueil", modifier = Modifier.weight(1f))
                        TextField(
                            value = note1,
                            onValueChange = { note1 = it },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = { }),
                            modifier = Modifier
                                .width(80.dp)
                                .padding(start = 8.dp)
                        )
                        Text(" / 5", modifier = Modifier.padding(start = 8.dp))
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Produits", modifier = Modifier.weight(1f))
                        TextField(
                            value = note2,
                            onValueChange = { note2 = it },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = { }),
                            modifier = Modifier
                                .width(80.dp)
                                .padding(start = 8.dp)
                        )
                        Text(" / 5", modifier = Modifier.padding(start = 8.dp))
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Présentation", modifier = Modifier.weight(1f))
                        TextField(
                            value = note3,
                            onValueChange = { note3 = it },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = { }),
                            modifier = Modifier
                                .width(80.dp)
                                .padding(start = 8.dp)
                        )
                        Text(" / 5", modifier = Modifier.padding(start = 8.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val noteDAO = NoteDAO(context)
                            val criteriaDAO = CriteriaDAO(context)

                            val accueilId = criteriaDAO.getCriteriaIdByTitle("accueil") ?: criteriaDAO.insertCriteria("accueil")
                            val produitsId = criteriaDAO.getCriteriaIdByTitle("produits") ?: criteriaDAO.insertCriteria("produits")
                            val presentationId = criteriaDAO.getCriteriaIdByTitle("présentation") ?: criteriaDAO.insertCriteria("présentation")

                            val value1 = note1.toIntOrNull() ?: 0
                            val value2 = note2.toIntOrNull() ?: 0
                            val value3 = note3.toIntOrNull() ?: 0

                            if (bakerySiren.isNotEmpty()) {
                                noteDAO.insertNote(value1, bakerySiren, accueilId)
                                noteDAO.insertNote(value2, bakerySiren, produitsId)
                                noteDAO.insertNote(value3, bakerySiren, presentationId)
                                Toast.makeText(context, "Notes soumises avec succès!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Erreur : Boulangerie non spécifiée.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Soumettre les Notes")
                    }
                } else {

                    Text("Code ticket incorrect ou non saisi", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
