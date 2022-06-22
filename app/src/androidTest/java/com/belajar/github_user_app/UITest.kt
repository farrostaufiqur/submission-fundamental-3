package com.belajar.github_user_app

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.belajar.github_user_app.ui.main.MainActivity
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UITest {
    private val dummyName = "farrostaufiqur"
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }
    @Test
    suspend fun favoriteTest() {
        delay(1500)
        Espresso.onView(withId(R.id.search_field))
            .perform(ViewActions.typeText(dummyName), ViewActions.closeSoftKeyboard())
        delay(1000)
        Espresso.onView(withId(R.id.rv_user)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.favorite)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.favorites)).perform(ViewActions.click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.favorite)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.favorites)).perform(ViewActions.click())
    }
    @Test
    fun settingTest(){
        Espresso.onView(withId(R.id.settings)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.switch_theme)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.switch_theme)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.switch_theme)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.switch_theme)).perform(ViewActions.click())
        Espresso.pressBack()
    }
}