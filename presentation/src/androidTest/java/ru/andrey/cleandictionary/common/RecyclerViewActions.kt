@file:Suppress("UNCHECKED_CAST")

package ru.andrey.cleandictionary.common

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf


/**
 *
 *
 *     Copy-pasted from espresso-contrib to change imported ViewAction package
 *
 *
 *
 *
 */


private const val NO_POSITION = -1

interface PositionableRecyclerViewAction : ViewAction {

    fun atPosition(position: Int): PositionableRecyclerViewAction
}

fun <VH : ViewHolder> scrollToPosition(position: Int): ViewAction {
    return ScrollToPositionViewAction(position)
}

fun <VH : ViewHolder> actionOnItemAtPosition(position: Int,
                                             viewAction: ViewAction): ViewAction {
    return ActionOnItemAtPositionViewAction<VH>(position, viewAction)
}

private class ActionOnItemAtPositionViewAction<VH : ViewHolder> constructor(private val position: Int, private val viewAction: ViewAction) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return allOf<View>(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun getDescription(): String {
        return ("actionOnItemAtPosition performing ViewAction: " + viewAction.description
                + " on item at position: " + position)
    }

    override fun perform(uiController: UiController, view: View) {
        val recyclerView = view as RecyclerView

        ScrollToPositionViewAction(position).perform(uiController, view)
        uiController.loopMainThreadUntilIdle()

        val viewHolderForPosition = recyclerView.findViewHolderForAdapterPosition(position) as VH

        val viewAtPosition = viewHolderForPosition.itemView

        viewAction.perform(uiController, viewAtPosition)
    }
}

private class ScrollToPositionViewAction constructor(private val position: Int) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return allOf<View>(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun getDescription(): String {
        return "scroll RecyclerView to position: $position"
    }

    override fun perform(uiController: UiController, view: View) {
        val recyclerView = view as RecyclerView
        recyclerView.scrollToPosition(position)
        uiController.loopMainThreadUntilIdle()
    }
}
