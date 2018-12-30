package ru.andrey.cleandictionary.presentation.view.list

import android.arch.persistence.room.Room
import android.content.Context
import android.support.v7.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.andrey.cleandictionary.R
import ru.andrey.cleandictionary.common.*
import ru.andrey.data.db.TranslationDatabase
import ru.andrey.data.db.entity.LanguageData
import ru.andrey.data.db.entity.TranslationData

@RunWith(AndroidJUnit4::class)
class WordListActivityFilledTest {

    @get:Rule
    var activityRule: ActivityTestRule<WordListActivity> = ActivityTestRule(WordListActivity::class.java, false, false)

    @Before
    fun setUp() {
        Room.databaseBuilder(ApplicationProvider.getApplicationContext(),
                TranslationDatabase::class.java,
                "translations_db")
                .build()
                .apply {
                    languageDao().apply {
                        save(LanguageData(0, "en"))
                        save(LanguageData(0, "ru"))
                    }
                    val lang1 = languageDao().findByCode("en")!!.id
                    val lang2 = languageDao().findByCode("ru")!!.id
                    translationDao().apply {
                        save(TranslationData(0, "text1", "text2", lang1, lang2, false))
                        save(TranslationData(0, "text1", "text2", lang1, lang2, false))
                        save(TranslationData(0, "favorite", "text2", lang1, lang2, true))
                        save(TranslationData(0, "text1", "text2", lang1, lang2, true))
                        save(TranslationData(0, "favorite2", "text2", lang1, lang2, false))
                    }
                }

        activityRule.launchActivity(null)
        waitData()
    }

    @After
    fun tearDown() {
        ApplicationProvider.getApplicationContext<Context>().deleteDatabase("translations_db")
        Thread.sleep(100)
    }

    @Test
    fun when_first_open_expect_no_hint() {
        onView(withId(R.id.hint))
                .check(matches(not(isDisplayed())))
    }

    @Test
    fun when_all_favorite_gone_show_hint() {
        onView(withId(R.id.favorite))
                .perform(ViewActions.click())

        clickStarOnFirstItem()

        clickStarOnFirstItem()

        onView(withId(R.id.hint))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.fav_word_hint)))
    }

    @Test
    fun when_click_favorite_keep_only_favorite() {

        clickStarOnFirstItem()

        onView(withId(R.id.favorite))
                .perform(ViewActions.click())

        onView(withId(R.id.recycler_view))
                .check(count(3))

        onView(isRoot())
                .perform(rotateLandscape())

        onView(withId(R.id.recycler_view))
                .check(count(3))

        clickStarOnFirstItem()

        onView(withId(R.id.recycler_view))
                .check(count(2))

        onView(isRoot())
                .perform(rotatePortrait())

        onView(withId(R.id.recycler_view))
                .check(count(2))
    }

    private fun clickStarOnFirstItem() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, clickRecyclerItem(R.id.star_image)))
        waitData()
    }

    private fun waitData() {
        onView(isRoot())
                .perform(waitFor(800))
    }
}
