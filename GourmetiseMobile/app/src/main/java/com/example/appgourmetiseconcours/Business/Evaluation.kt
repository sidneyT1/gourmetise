package com.example.appgourmetiseconcours.Business

data class Evaluation(
    val id: Int,  // Id de l'évaluation
    val bakery_siren: String,  // SIREN de la boulangerie
    val score: Int,  // Score de l'évaluation
    val evaluation_date: String  // Date de l'évaluation
)
