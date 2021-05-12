package com.decwujot.bitcoin.data.model

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class RatesResult(
    @SerializedName("USD_GBP")
    var gbp: JsonElement,
    @SerializedName("USD_EUR")
    val eur: JsonElement
) {

    fun getGBP(): Map<String?, Float?>? {
        val type = object :
            TypeToken<Map<String?, Float?>?>() {}.type
        return Gson().fromJson(this.gbp, type)
    }

    fun getEUR(): Map<String?, Float?>? {
        val type = object :
            TypeToken<Map<String?, Float?>?>() {}.type
        return Gson().fromJson(this.eur, type)
    }
}

fun RatesResult.toRateList(): List<Rate> {
    val list = mutableListOf<Rate>()
    list.addAll(
        listOf(
            Rate(this.gbp.asFloat),
            Rate(this.eur.asFloat)
        )
    )
    return list
}