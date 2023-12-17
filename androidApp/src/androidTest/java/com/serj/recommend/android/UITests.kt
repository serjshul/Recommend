package com.serj.recommend.android

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@OptIn(ExperimentalMaterialApi::class)
class UiTests {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule =
        createAndroidComposeRule<RecommendActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    /*
    @Test
    fun openHomeScreen() {
        rule.setContent {
            HomeScreen(
                //navController = rememberNavController()
            )
        }

        val nodeWithNorwegianForest =
            rule.onNodeWithText("Norwegian Forest")

        nodeWithNorwegianForest.assertExists()

        val sameNodeWithOtherRules = rule.onNode(
            hasText("Norwegian Forest")
                    and
                    hasText("Haruki Murakami")
        )

        sameNodeWithOtherRules.assertExists()

        Thread.sleep(1000)
        nodeWithNorwegianForest.performClick()
        Thread.sleep(2000)
        sameNodeWithOtherRules.performClick()



        assert(nodeWithNorwegianForest.onParent() == sameNodeWithOtherRules.onParent())
//        nodeWithNorwegianForest.onChildAt(3)

//        compareAuthorNamesOnScreen(
//            authorNameFromInteraction=,
//            authorName=
//        )

//        nodeWithNorwegianForest.performClick()
    }

//    @Test
//    fun compareAuthorNamesOnScreen(
//        authorNameFromInteraction:,
//        authorName: String
//    ) {
//        assert(authorName == authorNameFromInteraction)
//    }

     */

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