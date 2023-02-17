package com.example.meliapp.model.payment.installments


import com.google.gson.annotations.SerializedName

data class InstallmentsResponseItem(
    @SerializedName("agreements")
    val agreements: Any? = null,
    @SerializedName("issuer")
    val issuer: Issuer? = null,
    @SerializedName("merchant_account_id")
    val merchantAccountId: Any? = null,
    @SerializedName("payer_costs")
    val payerCosts: List<PayerCost?>? = null,
    @SerializedName("payment_method_id")
    val paymentMethodId: String? = null,
    @SerializedName("payment_type_id")
    val paymentTypeId: String? = null,
    @SerializedName("processing_mode")
    val processingMode: String? = null
)