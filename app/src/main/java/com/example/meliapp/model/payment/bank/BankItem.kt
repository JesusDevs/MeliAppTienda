package com.example.meliapp.model.payment.bank


import com.google.gson.annotations.SerializedName

data class BankItem(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("merchant_account_id")
    val merchantAccountId: Any? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("processing_mode")
    val processingMode: String? = null,
    @SerializedName("secure_thumbnail")
    val secureThumbnail: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null
)