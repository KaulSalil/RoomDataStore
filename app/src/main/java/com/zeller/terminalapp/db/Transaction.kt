package com.zeller.terminalapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val amount: Float,
    val isDeposit: Boolean
)