package com.decwujot.bitcoin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class Rate(
    val rate: Float
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}