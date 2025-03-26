package com.example.appgourmetiseconcours.Business

data class Evaluation(
    val bakery_siren: String,
    val score: Int,
    val evaluation_date: String,
    val ticketNum: String?
) {

}
