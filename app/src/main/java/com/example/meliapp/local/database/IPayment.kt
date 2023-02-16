package com.example.meliapp.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.meliapp.model.payment.PaymentMethodItem

@Dao
interface IPayment {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaymentMethod(list: List<PaymentMethodItem>)

}