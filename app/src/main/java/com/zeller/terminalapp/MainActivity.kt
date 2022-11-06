package com.zeller.terminalapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zeller.terminalapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.depositButton.setOnClickListener(this)
        binding.withdrawButton.setOnClickListener(this)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onClick(view: View?) {
        val amt: Float
        if (view?.id == R.id.withdrawButton) {
            val balance = mainViewModel?.balance
            if (!binding.amountInput.text.isNullOrEmpty()) {
                amt = binding.amountInput.text.toString().toFloat()
                if (balance != null) {
                    if (balance > amt) {
                        //MainViewModel.balance -= amt
                        mainViewModel?.debitBalance(amt)
                        binding.balance.text = mainViewModel?.balance.toString()
                        mainViewModel?.transactions?.addTransaction(
                            Transactions(
                                isDeposit = false,
                                amount = amt
                            )
                        )
                    } else {
                        Toast.makeText(this, "Not enough balance", Toast.LENGTH_LONG).show()
                    }
                }
            }

        } else if (view?.id == R.id.depositButton) {
            if (!binding.amountInput.text.isNullOrEmpty()) {
                amt = binding.amountInput.text.toString().toFloat()
                // MainViewModel.balance += amt
                mainViewModel?.creditBalance(amt)
                binding.balance.text = mainViewModel?.balance.toString()
                mainViewModel?.transactions?.addTransaction(
                    Transactions(
                        isDeposit = true,
                        amount = amt
                    )
                )
            }
        }
    }
}