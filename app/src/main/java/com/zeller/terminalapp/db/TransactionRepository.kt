package com.zeller.terminalapp.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.addTransaction(transaction)
    }

}