package com.example.meliapp.model.payment.method


import com.google.gson.annotations.SerializedName

data class CardNumber(
    @SerializedName("length")
    val length: Int? = null,
    @SerializedName("validation")
    val validation: String? = null
)