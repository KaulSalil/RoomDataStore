package com.zeller.terminalapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var transactions: TransactionsList = TransactionsList()

    private var _balance = MutableLiveData(0.0f)
    val balance: LiveData<Float> get() = _balance


    fun creditBalance(amt: Float) {
        _balance.value = _balance.value?.plus(amt)
    }

    fun debitBalance(amt: Float) {
        _balance.value = _balance.value?.minus(amt)
    }


}