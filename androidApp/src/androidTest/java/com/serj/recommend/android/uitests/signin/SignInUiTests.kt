package com.serj.recommend.android.uitests.signin

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@OptIn(ExperimentalTestApi::class, ExperimentalMaterialApi::class)
class SignInUiTests {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @OptIn(ExperimentalMaterialApi::class)
    @get:Rule(order = 1)
    val rule =
        createAndroidComposeRule<RecommendActivity>()

    @Test
    fun navigateFromSignInToSignUpScreens() {
        checkThatWeOnSignInScreenAndNavigateToSignUpScreen()
        checkThatWeOnSignUpScreen()
    }

    private fun checkThatWeOnSignInScreenAndNavigateToSignUpScreen() {
        val signUpMatcher = hasText(
            text = "sign up", ignoreCase = true
        )
        rule.waitUntilAtLeastOneExists(
            matcher = signUpMatcher,
            timeoutMillis = 1100 // wait here, because of Splash Screen have 1 second loading time
        )

        val signUpNode = rule.onNodeWithText(
            text = "Sign up", ignoreCase = true
        )
        signUpNode.assertExists()
        signUpNode.performClick()
    }

    private fun checkThatWeOnSignUpScreen() {
        val createAccountText = rule.activity.getString(R.string.create_account)
        val createAccountButtonMatcher = hasText(
            text = createAccountText, ignoreCase = true
        )
        rule.waitUntilAtLeastOneExists(
            matcher = createAccountButtonMatcher,
            timeoutMillis = 100
        )
        println("createAccountText: $createAccountText")
        val createAccountButtonNode = rule.onNodeWithText(
            text = createAccountText,
            ignoreCase = true
        )
        createAccountButtonNode.assertExists()
    }

}