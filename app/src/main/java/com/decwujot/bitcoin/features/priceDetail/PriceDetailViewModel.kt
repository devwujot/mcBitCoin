package com.decwujot.bitcoin.features.priceDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.decwujot.bitcoin.data.repository.BitCoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PriceDetailViewModel @Inject
constructor(
    repository: BitCoinRepository
) : ViewModel() {

    val rates = repository.getRates().asLiveData()
}