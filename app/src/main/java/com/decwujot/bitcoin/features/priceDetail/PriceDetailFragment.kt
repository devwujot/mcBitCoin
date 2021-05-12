package com.decwujot.bitcoin.features.priceDetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.decwujot.bitcoin.R
import com.decwujot.bitcoin.data.model.Price
import com.decwujot.bitcoin.data.model.Rate
import com.decwujot.bitcoin.databinding.FragmentPriceDetailBinding
import com.decwujot.bitcoin.utility.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.util.*

@AndroidEntryPoint
class PriceDetailFragment : Fragment() {

    private val viewModel: PriceDetailViewModel by viewModels()
    private val args: PriceDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentPriceDetailBinding
    lateinit var price: Price

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        price = args.price
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            rates.reObserve(
                viewLifecycleOwner,
                ratesObserver()
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun ratesObserver(): Observer<Resource<List<Rate>>> {
        return Observer { result ->
            result.data?.let {
                val lastSyncDate =
                    handleSynchronization()
                handleUIdata(it, lastSyncDate)
            }
            handleUIstate(result)
        }
    }

    private fun handleUIdata(
        it: List<Rate>,
        lastSyncDate: String
    ): Any {
        return when (it.isNullOrEmpty()) {
            true -> {
                binding.pricesSyncDate.visibility = View.GONE
            }
            else -> {
                binding.pricesSyncDate.visibility = View.VISIBLE
                val gbpRate = it[0].rate
                val eurRate = it[1].rate
                val gbpPrice = price.price * gbpRate
                val eurPrice = price.price * eurRate
                binding.apply {
                    cardUsd.isVisible = !it.isNullOrEmpty()
                    cardGbp.isVisible = !it.isNullOrEmpty()
                    cardEur.isVisible = !it.isNullOrEmpty()
                    pricesSyncDate.isVisible = !it.isNullOrEmpty()
                    pricesSyncDate.text = String.format(
                        requireContext().resources.getString(R.string.last_sync_date),
                        lastSyncDate
                    )
                    usdResult.text = String.format(
                        requireContext().resources.getString(R.string.price_detail_result),
                        resources.getString(R.string.bitCoin),
                        priceFormatter(price.price),
                        resources.getString(
                            R.string.dollar
                        )
                    )
                    gbpResult.text = String.format(
                        requireContext().resources.getString(R.string.price_detail_result),
                        resources.getString(R.string.bitCoin),
                        priceFormatter(gbpPrice),
                        resources.getString(
                            R.string.pound
                        )
                    )
                    eurResult.text = String.format(
                        requireContext().resources.getString(R.string.price_detail_result),
                        resources.getString(R.string.bitCoin),
                        priceFormatter(eurPrice),
                        resources.getString(
                            R.string.euro
                        )
                    )
                    pricesSyncDate.text = String.format(
                        requireContext().resources.getString(R.string.last_sync_date),
                        lastSyncDate
                    )
                }
            }
        }
    }

    private fun handleUIstate(result: Resource<List<Rate>>) {
        binding.apply {
            progressBar.isVisible =
                result is Resource.Loading && result.data.isNullOrEmpty()
            errorMessage.isVisible =
                result is Resource.Error && result.data.isNullOrEmpty()
            errorMessage.text = requireContext().resources.getString(R.string.network_error_message)
        }
    }

    private fun handleSynchronization(): String {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).apply {
            put(PREFS_SYNC_DETAIL, Date().formatDate())
        }
        val lastSyncDate =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .get(PREFS_SYNC_DETAIL, PREFS_SYNC_DEF_VAL)
        return lastSyncDate
    }

    private fun priceFormatter(price: Float): String {
        val myFormatter = DecimalFormat(resources.getString(R.string.price_format))
        return myFormatter.format(price)
    }
}