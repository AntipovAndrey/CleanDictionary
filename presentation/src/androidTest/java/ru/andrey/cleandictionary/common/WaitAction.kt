package ru.andrey.cleandictionary.common

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher

class WaitAction(private val millis: Int) : ViewAction {

    override fun getDescription(): String {
        return "wait for $millis"
    }

    override fun getConstraints(): Matcher<View> {
        return isRoot();
    }

    override fun perform(uiController: UiController, view: View) {
        uiController.loopMainThreadForAtLeast(millis.toLong());
    }
}

fun waitFor(millis: Int) = WaitAction(millis)
