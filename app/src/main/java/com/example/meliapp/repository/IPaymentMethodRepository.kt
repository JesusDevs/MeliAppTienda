package com.example.meliapp.repository

import com.example.meliapp.local.entity.ItemProductEntity
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.installments.InstallmentsResponseItem
import com.example.meliapp.model.payment.method.PaymentMethodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


interface IPaymentMethodRepository {
    suspend fun getPaymentMethods(): List<PaymentMethodItem>
    suspend fun getPaymentBank(paymentMethod:String): List<BankItem>
    suspend fun getInstallments(paymentMethod:String, bank:String, amount: Double): List<InstallmentsResponseItem>

    //base de datos room
     suspend fun insertProduct(productItem: ItemProductEntity)
    suspend fun getAllShoppingCart(): List<ItemProductEntity>
    suspend fun deleteAllProducts()

}