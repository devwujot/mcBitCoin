package com.decwujot.bitcoin.features.prices

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.decwujot.bitcoin.R
import com.decwujot.bitcoin.data.model.Price
import com.decwujot.bitcoin.databinding.FragmentPricesBinding
import com.decwujot.bitcoin.utility.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PricesFragment : Fragment() {

    private val viewModel: PricesViewModel by viewModels()
    lateinit var binding: FragmentPricesBinding
    lateinit var pricesAdapter: PricesAdapter
    lateinit var price: Price

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPricesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        pricesAdapter = PricesAdapter()
        pricesAdapter.onClickListener = { price ->
            navigateToPriceDetailFragment(price)
        }
        binding.apply {
            recyclerView.apply {
                adapter = pricesAdapter
            }
        }
    }

    private fun navigateToPriceDetailFragment(price: Price) {
        val action = PricesFragmentDirections.actionPricesFragmentToPriceDetailFragment(price)
        findNavController().navigate(action)
    }

    private fun initObservers() {
        with(viewModel) {
            prices.reObserve(
                viewLifecycleOwner,
                pricesObserver()
            )
        }
    }

    private fun pricesObserver(): Observer<Resource<List<Price>>> {
        return Observer { result ->
            result.data?.let {
                handleSynchronization(it)
            }
            pricesAdapter.submitList(result.data?.sortedByDescending { it.date })
            handleUIstates(result)
        }
    }

    private fun handleUIstates(result: Resource<List<Price>>) {
        binding.progressBar.isVisible =
            result is Resource.Loading && result.data.isNullOrEmpty()
        binding.errorMessage.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
        binding.errorMessage.text =
            requireContext().resources.getString(R.string.network_error_message)
    }

    private fun handleSynchronization(it: List<Price>) {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).apply {
            put(PREFS_SYNC_NAME, Date().formatDate())
        }
        val lastSyncDate =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .get(PREFS_SYNC_NAME, PREFS_SYNC_DEF_VAL)
        binding.pricesSyncDate.text = String.format(
            requireContext().resources.getString(R.string.last_sync_date),
            lastSyncDate
        )
        binding.pricesSyncDate.isVisible = !it.isNullOrEmpty()
    }
}