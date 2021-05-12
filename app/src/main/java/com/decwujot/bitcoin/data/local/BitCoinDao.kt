package com.decwujot.bitcoin.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decwujot.bitcoin.data.model.Price
import com.decwujot.bitcoin.data.model.Rate
import kotlinx.coroutines.flow.Flow

@Dao
interface BitCoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrices(prices: List<Price>)

    @Query("SELECT * FROM prices")
    fun getAllPrices(): Flow<List<Price>>

    @Query("DELETE FROM prices")
    suspend fun deleteAllPrices()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<Rate>)

    @Query("SELECT * FROM rates")
    fun getAllRates(): Flow<List<Rate>>

    @Query("DELETE FROM rates")
    suspend fun deleteAllRates()
}