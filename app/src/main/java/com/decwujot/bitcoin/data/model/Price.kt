package com.decwujot.bitcoin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "prices")
data class Price(
    var date: String,
    var price: Float,
    var gbpRate: Float? = null,
    var eurRate: Float? = null
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}