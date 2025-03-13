package com.example.appgourmetiseconcours.Business

data class ContestParams(
    val title: String,
    val description: String,
    val startRegistration: String,
    val endRegistration: String,
    val startEvaluation: String,
    val endEvaluation: String
)
