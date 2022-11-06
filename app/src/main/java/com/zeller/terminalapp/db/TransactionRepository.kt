package com.zeller.terminalapp.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()



    @WorkerThread
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.addTransaction(transaction)
    }

}