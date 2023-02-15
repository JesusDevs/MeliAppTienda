package com.example.meliapp.model.payment


import com.google.gson.annotations.SerializedName

data class Bin(
    @SerializedName("exclusion_pattern")
    val exclusionPattern: String? = null,
    @SerializedName("installments_pattern")
    val installmentsPattern: String? = null,
    @SerializedName("pattern")
    val pattern: String? = null
)