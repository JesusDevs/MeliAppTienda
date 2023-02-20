package com.example.meliapp.repository



import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.local.database.IPayment
import com.example.meliapp.local.database.ProductDatabase
import com.example.meliapp.local.entity.ItemProductEntity
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.installments.InstallmentsResponseItem
import com.example.meliapp.model.payment.method.PaymentMethodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentMethodRepository(private val dataSource: PaymentMethodDataSource,private val dao: IPayment): IPaymentMethodRepository {

    override suspend fun getPaymentMethods(): List<PaymentMethodItem> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getPaymentMethods()
        }
    }
    override suspend fun getPaymentBank(paymentMethod :String): List<BankItem> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getPaymentBank(paymentMethod = paymentMethod)
        }
    }

    override suspend fun getInstallments(paymentMethod: String, bank: String, amount: Double): List<InstallmentsResponseItem> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getPaymentInstallment(paymentMethod = paymentMethod, bank = bank, amount = amount)
        }
    }

    //base de datos room
    override suspend fun insertProduct(productItem: ItemProductEntity) {
        return  withContext(Dispatchers.IO) {
            return@withContext dao.insertItemProduct(productItem)
        }
    }
    override suspend fun getAllShoppingCart(): List<ItemProductEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllItemProducts()
        }
    }
}
