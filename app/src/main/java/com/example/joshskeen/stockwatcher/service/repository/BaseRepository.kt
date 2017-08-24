package com.example.joshskeen.stockwatcher.service.repository

import android.util.LruCache
import io.reactivex.Observable

abstract class BaseRepository {

    private val lruCache: LruCache<String, Observable<*>> = LruCache(50)
    internal val symbols = mutableSetOf<String>()

    fun <T> cacheObservable(symbol: String, observable: Observable<T>): Observable<T> =
            lruCache.get(symbol) as Observable<T>? ?: let {
                updateCache(symbol, observable)
                observable
            }

    private fun <T> updateCache(stockSymbol: String, observable: Observable<T>?) {
        lruCache.put(stockSymbol, observable!!)
    }

    fun clearCache() = lruCache.evictAll()

}
