package com.example.joshskeen.stockwatcher.service

data class StockSymbolError(override val message: String) : Exception()
