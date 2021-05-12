package com.decwujot.bitcoin.di

import android.app.Application
import androidx.room.Room
import com.decwujot.bitcoin.data.local.BitCoinDatabase
import com.decwujot.bitcoin.data.network.BitCoinApi
import com.decwujot.bitcoin.utility.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BitCoinApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideBitCoinApi(retrofit: Retrofit): BitCoinApi =
        retrofit.create(BitCoinApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): BitCoinDatabase =
        Room.databaseBuilder(app, BitCoinDatabase::class.java, DB_NAME)
            .build()
}