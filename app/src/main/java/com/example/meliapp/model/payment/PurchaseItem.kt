package com.example.meliapp.model.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseItem (
    var bank_name: String,
    var payment_method: String,
    val price: Double,
    val picture: String,
    var installments: Int) : Parcelable {

}
