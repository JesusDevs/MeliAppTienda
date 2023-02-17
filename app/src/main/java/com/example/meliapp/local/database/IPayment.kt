package com.example.meliapp.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.meliapp.model.payment.method.PaymentMethodItem

@Dao
interface IPayment {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaymentMethod(list: List<PaymentMethodItem>)

}