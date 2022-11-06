package com.zeller.terminalapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {


    @Insert
    suspend fun addTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions_table")
    fun getAllTransactions(): Flow<List<Transaction>>


}