package com.decwujot.bitcoin.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.decwujot.bitcoin.data.repository.BitCoinRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import java.lang.RuntimeException

abstract class BaseUnitTest {

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val repository: BitCoinRepository = mock()
    val exception = RuntimeException("Something went wrong")
}