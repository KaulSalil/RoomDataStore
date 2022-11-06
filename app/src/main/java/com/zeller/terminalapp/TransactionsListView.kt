package com.zeller.terminalapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeller.terminalapp.databinding.ActivityTransactionsListViewBinding

class TransactionsListView : AppCompatActivity() {

    var transactionListviewModel: TransactionListViewModel? = null
    private lateinit var activityTransactionsListViewBinding: ActivityTransactionsListViewBinding
    var adapter: TransactionListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTransactionsListViewBinding =
            ActivityTransactionsListViewBinding.inflate(layoutInflater)
        setContentView(activityTransactionsListViewBinding.root)
        transactionListviewModel = ViewModelProvider(
            this,
            TransactionListViewModel.TransactionListViewModelProvider((application as Application).repository)
        )[TransactionListViewModel::class.java]
        setUpRV()
        showTransactions()

    }

    private fun setUpRV() {
        adapter = TransactionListAdapter()
        activityTransactionsListViewBinding.transactionsRecyclerview.layoutManager =
            LinearLayoutManager(this)
        activityTransactionsListViewBinding.transactionsRecyclerview.adapter = adapter
    }

    private fun showTransactions() {
        transactionListviewModel?.getTransactions()?.observe(this) { transactions ->
            transactions.let {
                adapter?.submitList(it)
            }

        }
    }


}