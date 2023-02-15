package com.example.meliapp.model.payment


import com.google.gson.annotations.SerializedName

data class SecurityCode(
    @SerializedName("card_location")
    val cardLocation: String? = null,
    @SerializedName("length")
    val length: Int? = null,
    @SerializedName("mode")
    val mode: String? = null
)