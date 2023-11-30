package com.serj.recommend.android

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serj.recommend.android.ui.screens.home.HomeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTests {
    @get:Rule
    val rule = createComposeRule()

    val activityRule =
        createAndroidComposeRule<ComponentActivity>()

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
        TODO("Implement test @Dasha")
    }
}