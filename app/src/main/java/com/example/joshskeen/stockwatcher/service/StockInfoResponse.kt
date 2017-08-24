package com.example.joshskeen.stockwatcher.service

import com.google.gson.annotations.SerializedName

data class StockInfoResponse(
        @SerializedName("Status")
        private val status: String,
        @SerializedName("Name")
        private val name: String,
        @SerializedName("LastPrice")
        private val lastPrice: Double,
        @SerializedName("Change")
        private val change: Double,
        @SerializedName("ChangePercent")
        private val changePercent: Double,
        @SerializedName("MarketCap")
        private val marketCap: Long,
        @SerializedName("Volume")
        private val volume: Long,
        @SerializedName("ChangeYTD")
        private val changeYTD: Double,
        @SerializedName("ChangePercentYTD")
        private val changePercentYTD: Double,
        @SerializedName("High")
        private val high: Double,
        @SerializedName("Low")
        private val low: Double,
        @SerializedName("Open")
        private val open: Double)
