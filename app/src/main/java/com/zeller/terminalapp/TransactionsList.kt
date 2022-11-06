package com.zeller.terminalapp

import com.zeller.terminalapp.db.Transaction

class TransactionsList {
    private val transactionList: MutableList<Transaction> = mutableListOf()
    fun addTransaction(transaction: Transaction) {
        transactionList.add(transaction)
    }
}