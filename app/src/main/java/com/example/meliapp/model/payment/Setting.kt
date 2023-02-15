package com.example.meliapp.model.payment


import com.google.gson.annotations.SerializedName

data class Setting(
    @SerializedName("bin")
    val bin: Bin? = null,
    @SerializedName("card_number")
    val cardNumber: CardNumber? = null,
    @SerializedName("security_code")
    val securityCode: SecurityCode? = null
)