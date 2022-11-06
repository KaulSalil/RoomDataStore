package com.zeller.terminalapp

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var balance = 0.0f
    var transactions: TransactionsList = TransactionsList()

    fun creditBalance(amt: Float) {
        balance += amt
    }

    fun debitBalance(amt: Float) {
        balance -= amt
    }


}