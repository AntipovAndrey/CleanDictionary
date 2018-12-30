package ru.andrey.cleandictionary.common

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher

class OrientationChangeAction(private val orientation: Int) : ViewAction {

    override fun getDescription(): String {
        val mode = if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) "landscape" else "portrait"
        return "rotate to $mode"
    }

    override fun getConstraints(): Matcher<View> = isRoot()

    override fun perform(uiController: UiController, view: View) {
        uiController.loopMainThreadUntilIdle()
        val context = view.context
        val activity = if (context is Activity) {
            context
        } else {
            // meizu workaround
            val field = view.context.javaClass.getDeclaredField("mPhoneWindow");
            field.isAccessible = true
            val phoneWindow = field.get(context);
            val ctxReturner = phoneWindow.javaClass.getMethod("getContext");
            ctxReturner.invoke(phoneWindow) as Activity
        }
        activity.requestedOrientation = orientation
    }
}

fun rotateLandscape(): ViewAction = OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

fun rotatePortrait() = OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
