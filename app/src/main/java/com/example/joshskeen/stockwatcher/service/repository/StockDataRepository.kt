package com.example.joshskeen.stockwatcher.service.repository

import java.util.concurrent.TimeUnit

import io.reactivex.Observable

import com.example.joshskeen.stockwatcher.service.StockInfoForSymbol
import com.example.joshskeen.stockwatcher.service.StockInfoResponse
import com.example.joshskeen.stockwatcher.service.StockService
import com.example.joshskeen.stockwatcher.service.StockSymbol
import com.example.joshskeen.stockwatcher.service.StockSymbolError
import io.reactivex.functions.BiFunction

import timber.log.Timber

private const val CACHE_PREFIX_GET_STOCK_INFO = "stockInfo"
private const val CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL = "getStockInfoForSymbol"
private const val CACHE_PREFIX_GET_STOCK_SYMBOLS = "lookupStockSymbols"


class StockDataRepository(private val service: StockService) : BaseRepository() {

    fun getStockInfoForSymbol(symbol: String): Observable<StockInfoForSymbol>? {
        Timber.i("method: %s, symbol: %s", CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL, symbol)
        val stockInfoForSymbolObservable = Observable.combineLatest<StockSymbol, StockInfoResponse, StockInfoForSymbol>(
                lookupStockSymbol(symbol),
                fetchStockInfoFromSymbol(symbol),
                BiFunction<StockSymbol, StockInfoResponse, StockInfoForSymbol> { stockSymbol, stockInfoResponse ->
                    StockInfoForSymbol(stockSymbol, stockInfoResponse)
                })
        return cacheObservable<StockInfoForSymbol>(CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL + symbol, stockInfoForSymbolObservable)
    }

    private fun fetchStockInfoFromSymbol(symbol: String): Observable<StockInfoResponse> {
        return lookupStockSymbol(symbol)
                .flatMap { (symbol1) -> getStockInfo(symbol1) }
    }

    private fun lookupStockSymbol(symbol: String) = lookupStockSymbols(symbol)
            .doOnNext { if (it.isEmpty()) throw StockSymbolError("$symbol not found!") }
            .doOnNext { symbols.add(symbol) }
            .flatMap { t -> Observable.fromIterable(t) }
            .take(1)

    private fun lookupStockSymbols(symbol: String) =
            cacheObservable(CACHE_PREFIX_GET_STOCK_SYMBOLS + symbol, service.lookupStock(symbol).cache())

    private fun getStockInfo(symbol: String): Observable<StockInfoResponse>? {
        Timber.i("method: %s, symbol: %s", CACHE_PREFIX_GET_STOCK_INFO, symbol)
        //simulates the service taking a while to respond with data!
        val observableToCache = service.stockInfo(symbol).delay(3, TimeUnit.SECONDS).cache()
        return cacheObservable(CACHE_PREFIX_GET_STOCK_INFO + symbol, observableToCache)
    }

}
