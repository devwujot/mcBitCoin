package com.decwujot.bitcoin

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PriceDetailFeature : BaseUITest() {

    @Test
    fun displayScreenTitle() {
        navigateToPriceDetail(0)
        BaristaVisibilityAssertions.assertDisplayed("BitCoin Rates")
    }

    @Test
    fun displayUSDcardTitle() {
        navigateToPriceDetail(1)
        BaristaVisibilityAssertions.assertDisplayed("BTC  to USD")
    }

    @Test
    fun displayGBPcardTitle() {
        navigateToPriceDetail(2)
        BaristaVisibilityAssertions.assertDisplayed("BTC to GBP")
    }

    @Test
    fun displayEURcardTitle() {
        navigateToPriceDetail(3)
        BaristaVisibilityAssertions.assertDisplayed("BTC to EUR")
    }

    private fun navigateToPriceDetail(row: Int) {
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.item_date),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        withId(R.id.recycler_view),
                        row
                    )
                )
            )
        )
            .perform(ViewActions.click())
    }
}