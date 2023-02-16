package com.example.meliapp.repository

import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.method.PaymentMethodItem


interface IPaymentMethodRepository {
    suspend fun getPaymentMethods(): List<PaymentMethodItem>
    suspend fun getPaymentBank(paymentMethod:String): List<BankItem>

}