package com.passerby.cfttestproject.bussiness.models

data class DataModel(
    val bank: Bank,
    val brand: String,
    val country: Country,
    val number: Number,
    val prepaid: Boolean,
    val scheme: String,
    val type: String
)