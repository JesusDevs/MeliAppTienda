package com.example.meliapp.model.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseItem(
    var bank_name: String,
    var payment_method: String,
    val price: Double,
    val picture: String,
    var installments: Int,
    val id: String,
    val title: String?,
    val thumbnail: String?,
    val condition: String?,
    val description: String?,
    val quantity: Int?,
    val favorite: Boolean = false,
    val total: Double? = price.times(quantity ?: 0)
) : Parcelable {

}
