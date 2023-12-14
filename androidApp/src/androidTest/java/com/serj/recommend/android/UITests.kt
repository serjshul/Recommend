package com.serj.recommend.android

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serj.recommend.android.ui.screens.home.HomeScreen
import com.serj.recommend.android.ui.screens.home.HomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class UiTests {
    @get:Rule(order = 0)
    val rule = createComposeRule()

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    val activityRule =
        createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var viewModel: HomeViewModel

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
        TODO("Implement test @Dasha")
    }

    @Test
    fun clickButton() {
        //val model = HomeViewModel()
        rule.setContent { HomeScreen(openScreen = {}) }

        val nodeWithNorwegianForest =
            rule.onNodeWithText("Norwegian Forest")

        nodeWithNorwegianForest.assertExists()

    }
}