package com.example.meliapp.repository

import com.example.meliapp.model.payment.PaymentMethodItem


interface IPaymentMethodRepository {
    suspend fun getLatesGames(): List<PaymentMethodItem>

}