package com.serj.recommend.android

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serj.recommend.android.tests.MainScreenActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@OptIn(ExperimentalMaterialApi::class)
class UiTests {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule =
        createAndroidComposeRule<RecommendActivity>()
//     createComposeRule()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun openHomeScreen() {
        rule.setContent { MainScreenActivity() }

         val nodeWithReadText =
            rule.onNodeWithText("Read")

        rule.mainClock.advanceTimeUntil(3_000) {
            1 == 1
        }

        nodeWithReadText.assertExists()

//        nodeWithReadText.performClick()

//        val sameNodeWithOtherRules = rule.onNode(
//            hasText("Norwegian Forest")
//                    and
//                    hasText("Haruki Murakami")
//        )
//        sameNodeWithOtherRules.assertExists()
//        sameNodeWithOtherRules.performClick()

        // check that node parent are same
//        assert(nodeWithNorwegianForest.onParent() == sameNodeWithOtherRules.onParent())
//        nodeWithNorwegianForest.onChildAt(3)

//        compareAuthorNamesOnScreen(
//            authorNameFromInteraction=,
//            authorName=
//        )

//        nodeWithNorwegianForest.performClick()
    }

    private fun compareAuthorNamesOnScreen(
        authorNameFromInteraction: String,
        authorName: String
    ) {
        assert(authorName == authorNameFromInteraction)
    }


    fun homeScreenCheckNavigationByMediaCategoryAndCompareTitleNames() {

    }

    fun checkThatBottomNavigationBarIncludesFiveButtons() {
        TODO("Implement test @Daxavic")
    }

    @Test
    fun clickButton() {
        val nodeWithNorwegianForest =
            rule.onNodeWithText("Norwegian Forest")

        nodeWithNorwegianForest.assertDoesNotExist()
    }

    @Test
    fun checkNotifications() {
        // P.S. for TODO, when RecommendActivity is starts,
        //  its request permission notifications in dialog window,
        //  check that this window is appearing
        TODO(
            "Check that notification dialog window appears." +
                    "Task for @Daxavic"
        )
    }

    @Test
    fun allowNotifications() {
        TODO(
            "Pass notification dialog window" +
                    "& check that notification window is out." +
                    "Task for @Daxavic"
        )
    }
}