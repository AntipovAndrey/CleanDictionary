package ru.andrey.cleandictionary.common

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher

class ClickRecyclerItem(@param:IdRes private val id: Int) : ViewAction {

    override fun getDescription(): String {
        return "click on recycler's item view: $id"
    }

    override fun getConstraints(): Matcher<View> {
        return isDisplayed()
    }

    override fun perform(uiController: UiController?, view: View) {
        view.findViewById<View>(id).performClick()
    }
}

fun clickRecyclerItem(@IdRes id: Int) = ClickRecyclerItem(id)
