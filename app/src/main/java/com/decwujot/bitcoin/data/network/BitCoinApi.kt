package com.decwujot.bitcoin.data.network

import com.decwujot.bitcoin.data.model.PriceResult
import com.decwujot.bitcoin.data.model.RatesResult
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface BitCoinApi {

    companion object {
        const val BASE_URL = "https://api.coindesk.com/v1/bpi/"
        const val BASE_URL_RATE_API =
            "https://free.currconv.com/api/v7/convert?q=USD_GBP,USD_EUR&compact=ultra&apiKey=d8ea0e3284cabc5907e5"
    }

    @GET("historical/close.json")
    suspend fun getHistoricalPrices(
        @Query("start") startDate: String,
        @Query("end") endDate: String,
    ): PriceResult

    @GET
    suspend fun getRates(
        @Url url: String = BASE_URL_RATE_API
    ): RatesResult
}