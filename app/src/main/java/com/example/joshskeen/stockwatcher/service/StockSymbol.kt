package com.example.joshskeen.stockwatcher.service

import com.google.gson.annotations.SerializedName

data class StockSymbol(
        @SerializedName("Symbol")
        val symbol: String,
        @SerializedName("Name")
        val name: String)