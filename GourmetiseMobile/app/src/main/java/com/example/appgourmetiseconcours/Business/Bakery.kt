package com.example.appgourmetiseconcours.Business

data class Bakery(
    val siren: String,
    val name: String,
    val street: String,
    val postcode: String,
    val city: String,
    val phonenumber: String,
    val contactname: String,
    val description: String?,
    val ticketNum: String?,
    val evaluationDate: String?
)
