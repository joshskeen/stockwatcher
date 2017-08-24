package com.example.joshskeen.stockwatcher.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.joshskeen.stockwatcher.R
import com.example.joshskeen.stockwatcher.databinding.FragmentStockInfoBinding
import com.example.joshskeen.stockwatcher.extensions.component

import javax.inject.Inject

import io.reactivex.Observable

import com.example.joshskeen.stockwatcher.service.StockInfoForSymbol
import com.example.joshskeen.stockwatcher.service.repository.StockDataRepository
import com.example.joshskeen.stockwatcher.util.*

class StockInfoFragment : RxFragment() {

    @Inject
    lateinit var stockDataRepository: StockDataRepository
    private lateinit var binding: FragmentStockInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_info, container, false)
        return binding.apply {
            fetchDataButton.setOnClickListener {
                errorMessage.visibility = View.GONE
                loadRxData()
            }
            clearCacheButton.setOnClickListener {
                stockDataRepository.clearCache()
                Toast.makeText(context, "observable cache cleared!", Toast.LENGTH_LONG).show()
            }
        }.root
    }

    override fun loadRxData() {
        Observable.just(binding.tickerSymbol.text.toString())
                .filter(String::isNotEmpty)
                .singleOrError()
                .toObservable()
                .flatMap(stockDataRepository::getStockInfoForSymbol)
                .applyUIDefaults(this)
                .subscribe(this::displayStockResults, this::displayErrors)
    }

    private fun displayErrors(throwable: Throwable) {
        binding.errorMessage.apply {
            visibility = View.VISIBLE
            text = throwable.message
        }
    }

    private fun displayStockResults(stockInfoForSymbol: StockInfoForSymbol) {
        binding.stockValue.text = stockInfoForSymbol.toString()
    }
}
