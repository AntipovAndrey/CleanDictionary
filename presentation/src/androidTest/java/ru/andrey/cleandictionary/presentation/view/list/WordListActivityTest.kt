package ru.andrey.cleandictionary.presentation.view.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.andrey.cleandictionary.R
import ru.andrey.cleandictionary.common.rotateLandscape
import ru.andrey.cleandictionary.presentation.view.addword.AddWordActivity

@RunWith(AndroidJUnit4::class)
class WordListActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<WordListActivity> = ActivityTestRule(WordListActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(AddWordActivity::class.java)

    @Test
    fun when_first_open_expect_hint() {
        onView(withId(R.id.hint))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.add_word_hint)))
    }

    @Test
    fun when_first_open_and_click_favorites_expect_hint() {
        onView(withId(R.id.favorite))
                .perform(click())
        onView(withId(R.id.hint))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.fav_word_hint)))
    }

    @Test
    fun when_open_and_click_favorites_and_rotate_expect_favorite_enabled() {
        onView(withId(R.id.favorite))
                .perform(click())
        onView(isRoot())
                .perform(rotateLandscape())
        onView(withId(R.id.hint))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.fav_word_hint)))
    }

    @Test
    fun when_button_clicked_open_add_screen() {
        onView(withId(R.id.add_button))
                .perform(click())

        intended(hasComponent(AddWordActivity::class.java.name))
    }
}
