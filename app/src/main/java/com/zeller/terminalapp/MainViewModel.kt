package com.zeller.terminalapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.zeller.terminalapp.db.Transaction
import com.zeller.terminalapp.db.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "balance_details")


class MainViewModel(application: Application, val repository: TransactionRepository) :
    AndroidViewModel(application) {
    var transactions: TransactionsList = TransactionsList()
    val KEY_BALANCE = floatPreferencesKey("key_balance")
    private var _balance = MutableLiveData(0.0f)
    val balance: LiveData<Float> get() = _balance


    //ToDo:Better way to do this something similar convert Flow to Live Data
    fun updateLiveDataFromDataStore() {
        viewModelScope.launch {
            getApplication<Application>().dataStore.data.catch { exception ->
                if (exception is IOException) {
                    Log.e("MainViewModel", "Error reading preferences: ", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }

            }.map { balance_details ->
                {
                    balance_details[KEY_BALANCE]

                }
            }.flowOn(Dispatchers.IO).collect {
                _balance.value = it.invoke()
            }
        }

    }

    fun creditBalance(amt: Float) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { balance_details ->
                val currentBalance = balance_details[KEY_BALANCE] ?: 0.0f
                balance_details[KEY_BALANCE] = currentBalance + amt
                addTransaction(Transaction(amount = amt, isDeposit = true))
            }
        }
    }

    fun debitBalance(amt: Float) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { balance_details ->
                val currentBalance = balance_details[KEY_BALANCE] ?: 0.0f
                balance_details[KEY_BALANCE] = currentBalance - amt
                addTransaction(Transaction(amount = amt, isDeposit = false))
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.async {
            repository.insertTransaction(transaction)
        }

    }


    class MainViewModelFactory(
        val application: Application, private val repository: TransactionRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(application = application, repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}

