package com.decwujot.bitcoin.data.model

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class PriceResult(
    @SerializedName("bpi")
    val priceRecords: JsonElement,
    val disclaimer: String,
    val time: ResultTime
) {
    fun getResults(): Map<String?, Float?>? {
        val type = object :
            TypeToken<Map<String?, Float?>?>() {}.type
        return Gson().fromJson(priceRecords, type)
    }
}

data class ResultTime(
    val updated: String,
    val updatedISO: String
)

fun PriceResult.toPriceList(): List<Price> {
    val list = mutableListOf<Price>()
    this.getResults()?.forEach { (key, value) ->
        list.add(
            Price(
                date = key!!,
                price = value!!
            )
        )
    }
    return list
}