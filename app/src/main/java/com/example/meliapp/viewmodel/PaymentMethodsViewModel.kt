package com.example.meliapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.meliapp.core.status.Response
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.repository.IPaymentMethodRepository
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
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

class PaymentViewModelFactory(private val repo: IPaymentMethodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IPaymentMethodRepository::class.java).newInstance(repo)
    }
}

}

