package com.decwujot.bitcoin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.decwujot.bitcoin.data.model.Price
import com.decwujot.bitcoin.data.model.Rate

@Database(entities = [Price::class, Rate::class], version = 1, exportSchema = false)
abstract class BitCoinDatabase : RoomDatabase() {

    abstract fun bitCoinDao(): BitCoinDao
}