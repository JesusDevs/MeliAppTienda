package com.example.meliapp.model.payment.installments


import com.google.gson.annotations.SerializedName

data class PayerCost(
    @SerializedName("discount_rate")
    val discountRate: Int? = null,
    @SerializedName("installment_amount")
    val installmentAmount: Double? = null,
    @SerializedName("installment_rate")
    val installmentRate: Double? = null,
    @SerializedName("installment_rate_collector")
    val installmentRateCollector: List<String?>? = null,
    @SerializedName("installments")
    val installments: Int? = null,
    @SerializedName("labels")
    val labels: List<String?>? = null,
    @SerializedName("max_allowed_amount")
    val maxAllowedAmount: Int? = null,
    @SerializedName("min_allowed_amount")
    val minAllowedAmount: Int? = null,
    @SerializedName("payment_method_option_id")
    val paymentMethodOptionId: String? = null,
    @SerializedName("recommended_message")
    val recommendedMessage: String? = null,
    @SerializedName("reimbursement_rate")
    val reimbursementRate: Any? = null,
    @SerializedName("total_amount")
    val totalAmount: Double? = null
)