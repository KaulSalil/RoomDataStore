package com.zeller.terminalapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeller.terminalapp.db.Transaction

class TransactionListAdapter :
    ListAdapter<Transaction, TransactionListAdapter.TransactionVH>(TransactionsComparator()) {

    class TransactionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val transactionId: TextView = itemView.findViewById(R.id.transactionId)
        private val transactionAmt: TextView = itemView.findViewById(R.id.transactionAmt)
        private val transactionType: TextView = itemView.findViewById(R.id.transactionType)

        fun bind(transaction: Transaction) {
            transactionId.text = transaction.uid.toString()
            transactionAmt.text = transaction.amount.toString()
            if (transaction.isDeposit) {
                transactionType.text = itemView.context.getString(R.string.credit)
            } else {
                transactionType.text = itemView.context.getString(R.string.debit)
            }


        }

        companion object {
            fun createVH(parent: ViewGroup): TransactionVH {
                val view: View = LayoutInflater.from(parent.context).inflate(
                    R.layout.transaction_item, parent, false
                )
                return TransactionVH(view)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionVH {
        return TransactionVH.createVH(parent)
    }

    override fun onBindViewHolder(holder: TransactionVH, position: Int) {
        val transaction = getItem(position)
        transaction?.let {
            holder.bind(it)
        }

    }

    class TransactionsComparator : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return (oldItem.isDeposit == newItem.isDeposit && oldItem.amount == newItem.amount)
        }

    }


}