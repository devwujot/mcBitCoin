package com.decwujot.bitcoin.repository

import com.decwujot.bitcoin.data.local.BitCoinDao
import com.decwujot.bitcoin.data.local.BitCoinDatabase
import com.decwujot.bitcoin.data.model.Price
import com.decwujot.bitcoin.data.model.PriceResult
import com.decwujot.bitcoin.data.model.ResultTime
import com.decwujot.bitcoin.data.network.BitCoinApi
import com.decwujot.bitcoin.data.repository.BitCoinRepository
import com.decwujot.bitcoin.utility.Resource
import com.decwujot.bitcoin.utility.formatDate
import com.decwujot.bitcoin.utils.BaseUnitTest
import com.google.gson.JsonElement
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class BitCoinRepositoryShould : BaseUnitTest() {

    private val api: BitCoinApi= mock()
    private val db: BitCoinDatabase = mock()
    private lateinit var startDate: String
    private lateinit var endDate: String
    private lateinit var dateRange: Pair<String, String>

    @Before
    fun beforeTest() {
        dateRange = dateGenerator(14)

    }


    private suspend fun mockFailureCase(): BitCoinRepository {
        whenever(db.bitCoinDao().getAllPrices()).thenReturn(
            flow {
                emit(listOf<Price>())
            }
        )
        return BitCoinRepository(api, db)
    }

    private fun dateGenerator(daysAmmount: Int): Pair<String, String> {
        val calendar = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.DATE, -daysAmmount)
        }
        val endDate = Date().formatDate()
        val startDate = calendar.time.formatDate()
        return Pair(startDate, endDate)
    }
}