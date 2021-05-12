package com.decwujot.bitcoin.data.repository

import androidx.room.withTransaction
import com.decwujot.bitcoin.data.local.BitCoinDatabase
import com.decwujot.bitcoin.data.model.toPriceList
import com.decwujot.bitcoin.data.model.toRateList
import com.decwujot.bitcoin.data.network.BitCoinApi
import com.decwujot.bitcoin.utility.networkBoundResource
import javax.inject.Inject

class BitCoinRepository @Inject constructor(
    private val api: BitCoinApi,
    private val db: BitCoinDatabase
) {

    private val bitCoinDao = db.bitCoinDao()

    fun getHistoricalPrices(startDate: String, endDate: String) = networkBoundResource(
        query =
        {
            bitCoinDao.getAllPrices()
        },
        fetch =
        {
            api.getHistoricalPrices(startDate, endDate)
        },
        saveFetchResult =
        { prices ->
            db.withTransaction {
                bitCoinDao.deleteAllPrices()
                bitCoinDao.insertPrices(prices.toPriceList())
            }
        }
    )

    fun getRates() = networkBoundResource(
        query =
        {
            bitCoinDao.getAllRates()
        },
        fetch =
        {
            api.getRates()
        },
        saveFetchResult =
        { rates ->
            db.withTransaction {
                bitCoinDao.deleteAllRates()
                bitCoinDao.insertRates(rates.toRateList())
            }
        }
    )
}