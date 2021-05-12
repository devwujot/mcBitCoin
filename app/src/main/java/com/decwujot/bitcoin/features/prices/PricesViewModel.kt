package com.decwujot.bitcoin.features.prices

import androidx.lifecycle.*
import com.decwujot.bitcoin.data.repository.BitCoinRepository
import com.decwujot.bitcoin.utility.DATE_RANGE
import com.decwujot.bitcoin.utility.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PricesViewModel @Inject
constructor(
    repository: BitCoinRepository
) : ViewModel() {

    val dateRange = dateGenerator(DATE_RANGE)
    val start = dateRange.first
    val end = dateRange.second

    val prices = repository.getHistoricalPrices(start, end).asLiveData()

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