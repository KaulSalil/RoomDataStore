package com.zeller.terminalapp

import com.zeller.terminalapp.db.AppDatabase
import com.zeller.terminalapp.db.TransactionRepository

class Application : android.app.Application() {
    //In case need coroutinescrope inside the database class
    //val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TransactionRepository(database.getTransactionDao()) }

}