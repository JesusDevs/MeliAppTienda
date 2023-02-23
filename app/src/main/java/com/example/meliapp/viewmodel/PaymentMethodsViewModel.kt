package com.example.meliapp.viewmodel

import androidx.lifecycle.*
import com.example.meliapp.core.status.Response
import com.example.meliapp.local.entity.ItemProductEntity
import com.example.meliapp.model.payment.PurchaseItem
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.installments.InstallmentsResponseItem
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.repository.IPaymentMethodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PaymentMethodsViewModel(private val repo: IPaymentMethodRepository): ViewModel() {
    
    suspend fun getPaymentMethods() : StateFlow<Response<List<PaymentMethodItem>>> = flow {
        kotlin.runCatching {
            repo.getPaymentMethods()
        }.onFailure { throwable ->
            emit(Response.error(data = null, message = throwable.message ?: "Error Occurred!"))
        }.onSuccess { platform ->
            emit(Response.success(data = platform))
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000), // Or Lazily because it's a one-shot
        initialValue = Response.loading(data = null)
    )

    suspend fun getPaymentBanks(paymentMethod:String) : StateFlow<Response<List<BankItem>>> = flow {
        kotlin.runCatching {
            repo.getPaymentBank(paymentMethod)
        }.onFailure { throwable ->
            emit(Response.error(data = null, message = throwable.message ?: "Error Occurred!"))
        }.onSuccess { platform ->
            emit(Response.success(data = platform))
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000), // Or Lazily because it's a one-shot
        initialValue = Response.loading(data = null)
    )

    suspend fun getPaymentInstalment(paymentMethod:String, bank:String, amount: Double) : StateFlow<Response<List<InstallmentsResponseItem>>> = flow {
        kotlin.runCatching {
            repo.getInstallments(paymentMethod, bank, amount)
        }.onFailure { throwable ->
            emit(Response.error(data = null, message = throwable.message ?: "Error Occurred!"))
        }.onSuccess { platform ->
            emit(Response.success(data = platform))
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000), // Or Lazily because it's a one-shot
        initialValue = Response.loading(data = null)
    )
    fun getShoppingCart() : StateFlow<Response<List<ItemProductEntity>>> = flow {
        kotlin.runCatching {
            repo.getAllShoppingCart()
        }.onFailure { throwable ->
            emit(Response.error(data = null, message = throwable.message ?: "Error Occurred!"))
        }.onSuccess { platform ->
            emit(Response.success(data = platform))
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000), // Or Lazily because it's a one-shot
        initialValue = Response.loading(data = null)
    )

    fun deleteAllProducts(){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteAllProducts()
        }
    }
    fun insertProduct(product: ItemProductEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertProduct(product)
        }
    }
    class PaymentViewModelFactory(private val repo: IPaymentMethodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IPaymentMethodRepository::class.java).newInstance(repo)
    }
}

}

