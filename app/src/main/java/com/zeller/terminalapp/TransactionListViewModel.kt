package com.zeller.terminalapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zeller.terminalapp.db.Transaction
import com.zeller.terminalapp.db.TransactionRepository

class TransactionListViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    //To Prevent the view from directly modifying the underlying livedata
    fun getTransactions(): LiveData<List<Transaction>> {
        return repository.allTransactions
    }

    class TransactionListViewModelProvider(private val repository: TransactionRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TransactionListViewModel::class.java)) {
                return TransactionListViewModel(repository) as T
            }
            throw IllegalArgumentException("Invalid class provided")
        }

    }

}