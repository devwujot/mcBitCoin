package com.decwujot.bitcoin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decwujot.bitcoin.utility.formatDate
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import org.junit.Test
import org.junit.runner.RunWith
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import java.util.*

@RunWith(AndroidJUnit4::class)
class PricesFeature : BaseUITest() {

    @Test
    fun displayScreenTitle() {
        assertDisplayed("BitCoin")
    }

    @Test
    fun displayListOfPrices() {

        val calendar = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.DATE, -1)
        }
        val currentDate = calendar.time.formatDate()

        BaristaRecyclerViewAssertions
            .assertRecyclerViewItemCount(R.id.recycler_view, 14)

        onView(
            CoreMatchers.allOf(
                withId(R.id.item_date),
                isDescendantOfA(nthChildOf(withId(R.id.recycler_view), 0))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText(currentDate)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigateToDetailScreen() {

        onView(
            CoreMatchers.allOf(
                withId(R.id.item_date),
                isDescendantOfA(nthChildOf(withId(R.id.recycler_view), 0))
            )
        )
            .perform(ViewActions.click())

        assertDisplayed(R.id.prices_detail_root)
    }
}