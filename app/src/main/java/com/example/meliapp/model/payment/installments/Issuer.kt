package com.example.meliapp.model.payment.installments


import com.google.gson.annotations.SerializedName

data class Issuer(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("secure_thumbnail")
    val secureThumbnail: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null
)