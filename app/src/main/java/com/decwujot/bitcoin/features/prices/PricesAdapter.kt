package com.decwujot.bitcoin.features.prices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.decwujot.bitcoin.R
import com.decwujot.bitcoin.data.model.Price
import com.decwujot.bitcoin.databinding.ItemPriceBinding
import java.text.DecimalFormat

class PricesAdapter() :
    ListAdapter<Price, PricesAdapter.PricesViewHolder>(PriceComparator()) {

    lateinit var onClickListener: (Price) -> Unit

    class PricesViewHolder(
        private val binding: ItemPriceBinding,
        private val listener: (Price) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(price: Price) {
            binding.apply {
                itemDate.text = price.date
                val myFormatter =
                    DecimalFormat(this.root.resources.getString(R.string.price_format))
                itemPrice.text = String.format(
                    this.root.resources.getString(R.string.item_price),
                    myFormatter.format(price.price)
                )
                binding.root.setOnClickListener {
                    listener.invoke(price)
                }
            }
        }
    }

    class PriceComparator : DiffUtil.ItemCallback<Price>() {
        override fun areItemsTheSame(oldItem: Price, newItem: Price) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Price, newItem: Price) =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PricesViewHolder {
        val binding =
            ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PricesViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: PricesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}