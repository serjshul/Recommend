package com.serj.recommend.android

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serj.recommend.android.tests.MainScreenActivity
import com.serj.recommend.android.ui.components.authentication.AUTHENTICATION_EMAIL_FIELD_TT
import com.serj.recommend.android.ui.components.authentication.AUTHENTICATION_PASSWORD_FIELD_TT
import com.serj.recommend.android.ui.screens.authentication.resetPassword.RESET_PASSWORD_BUTTON_TT
import com.serj.recommend.android.ui.screens.authentication.signIn.AUTHENTICATION_FORGOT_PASSWORD_BUTTON_TT
import com.serj.recommend.android.ui.screens.authentication.signIn.AUTHENTICATION_SIGN_IN_BUTTON_TT
import com.serj.recommend.android.ui.screens.authentication.signIn.AUTHENTICATION_SIGN_UP_BUTTON_TT
import com.serj.recommend.android.ui.screens.authentication.signUp.SIGNUP_SCREEN_SIGN_UP_BUTTON_TT
import com.serj.recommend.android.ui.screens.authentication.signUp.SIGN_UP_PASSWORDS_FIELD_TT
import com.serj.recommend.android.ui.screens.authentication.signUp.SIGN_UP_REPEAT_PASSWORDS_FIELD_TT
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


const val EMAIL_TC = "daxavic@yandex.ru"
const val PASSWORD_TC = "123Qwerty"
const val SLEEP_TIME_TC: Long  = 20000
const val UNREGISTERED_EMAIL_TC = "d@yandex.ru"
const val PASSWORD_FOR_UNREGISTERED_EMAIL_TC = "123Qwe"

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

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun completeUserAuthentication() {
        rule.apply {
            waitUntilNodeCount(hasText("Email"), 1, SLEEP_TIME_TC)

            onNodeWithTag(AUTHENTICATION_EMAIL_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(EMAIL_TC)

            onNodeWithTag(AUTHENTICATION_PASSWORD_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(PASSWORD_TC)

            onNodeWithTag(AUTHENTICATION_SIGN_IN_BUTTON_TT)
                .performClick()

           waitUntilNodeCount(hasText("Read"), 1, SLEEP_TIME_TC)

            onNodeWithText("Read")
                .assertIsDisplayed()
        }
    }
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun makeAuthenticationEmptyPassword() {
        rule.apply {
            waitUntilNodeCount(hasText("Email"), 1, SLEEP_TIME_TC)

            onNodeWithTag(AUTHENTICATION_EMAIL_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(EMAIL_TC)

            onNodeWithTag(AUTHENTICATION_SIGN_IN_BUTTON_TT)
                .performClick()

            onNodeWithText(rule.activity.getString(R.string.empty_password_error))
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun enteredPasswordIsMaskedThenOpensThenMaskedThen() {
        rule.apply {
            waitUntilNodeCount(hasText("Email"), 1, SLEEP_TIME_TC)

            onNodeWithTag(AUTHENTICATION_PASSWORD_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(PASSWORD_TC)

            onNodeWithTag(AUTHENTICATION_PASSWORD_FIELD_TT)
                .assertTextContains("•••••••••")

            onNodeWithContentDescription("Visibility")
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(AUTHENTICATION_PASSWORD_FIELD_TT)
                .assertTextContains(PASSWORD_TC)

            onNodeWithContentDescription("Visibility")
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(AUTHENTICATION_PASSWORD_FIELD_TT)
                .assertTextContains("•••••••••")

        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun forgotPasswordAndActionsOnSignInScreen() {
        rule.apply {
            waitUntilNodeCount(hasText("Email"), 1, SLEEP_TIME_TC)

            onNodeWithTag(AUTHENTICATION_FORGOT_PASSWORD_BUTTON_TT)
                .assertIsDisplayed()
                .performClick()

            waitUntilNodeCount(hasTestTag(RESET_PASSWORD_BUTTON_TT), 1, SLEEP_TIME_TC)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun makeAuthenticationIncorrectPassword() {
        //val error = "The supplied auth credential is incorrect, malformed or has expired."
        rule.apply {
            waitUntilNodeCount(hasText("Email"), 1, SLEEP_TIME_TC)

            onNodeWithTag(AUTHENTICATION_EMAIL_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(EMAIL_TC)

            onNodeWithTag(AUTHENTICATION_PASSWORD_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput("111")

            onNodeWithTag(AUTHENTICATION_SIGN_IN_BUTTON_TT)
                .performClick()

            waitUntilNodeCount(hasText("Error"), 1, SLEEP_TIME_TC)

            onNodeWithText(rule.activity.getString(R.string.error_sign_in))
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun goFromSignInScreenToSignUpScreen() {
        rule.apply {
            waitUntilNodeCount(hasText("Email"), 1, SLEEP_TIME_TC)

            onNodeWithTag(AUTHENTICATION_SIGN_UP_BUTTON_TT)
                .assertIsDisplayed()
                .performClick()

            waitUntilNodeCount(hasTestTag(
                SIGNUP_SCREEN_SIGN_UP_BUTTON_TT),
                1,
                SLEEP_TIME_TC
            )

            onNodeWithTag(SIGNUP_SCREEN_SIGN_UP_BUTTON_TT)
                .assertIsDisplayed()
        }
    }

    //SignUpScreen

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun checkExistingEmailForRegistrationUser(){
        val messageError = "The email address is already in use by another account."
        goFromSignInScreenToSignUpScreen()

        rule.apply {
            onNodeWithTag(AUTHENTICATION_EMAIL_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(EMAIL_TC)

            onNodeWithTag(SIGN_UP_PASSWORDS_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(PASSWORD_FOR_UNREGISTERED_EMAIL_TC)

            onNodeWithTag(SIGN_UP_REPEAT_PASSWORDS_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(PASSWORD_FOR_UNREGISTERED_EMAIL_TC)

            onNodeWithTag(SIGNUP_SCREEN_SIGN_UP_BUTTON_TT)
                .assertIsDisplayed()
                .performClick()

            waitUntilNodeCount(hasText(messageError), 1, SLEEP_TIME_TC)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun checkEnteringIncorrectPassword() {
         val incorrectPassword = "12Qw"

        goFromSignInScreenToSignUpScreen()

        rule.apply {
            onNodeWithTag(AUTHENTICATION_EMAIL_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(UNREGISTERED_EMAIL_TC)

            onNodeWithTag(SIGN_UP_PASSWORDS_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(incorrectPassword)

            onNodeWithTag(SIGN_UP_REPEAT_PASSWORDS_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(incorrectPassword)

            onNodeWithTag(SIGNUP_SCREEN_SIGN_UP_BUTTON_TT)
                .assertIsDisplayed()
                .performClick()

            waitUntilNodeCount(hasText(
                rule.activity.getString(R.string.password_error)),
                1,
                SLEEP_TIME_TC)
        }

    }




    @OptIn(ExperimentalTestApi::class)
    @Test
    fun openHomeScreen() {
        rule.setContent { MainScreenActivity() }
        rule.apply{
            waitUntilNodeCount(hasText("Read"), 1, SLEEP_TIME_TC)
            onNodeWithText("Read")
                .assertIsDisplayed()

        }
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

    @Test
    fun accessImageTest() {
        // мысли как можно делать - но походу не то
        val assetManager = rule.activity.assets
        assetManager.open("")
        val testUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000020"

        // идешь на экрана NewRecommendationScreen
        // кликаешь на кнопку add a background / add a cover

        val resultData = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val selectedData = resultData.data

        // TODO: Get Uri of image-element from Compose-tree
        val onViewElementData = Uri.EMPTY

        assert(selectedData == onViewElementData)

        // source:  https://stackoverflow.com/questions/32142463/how-to-stub-select-images-intent-using-espresso-intents
//        intending(not(isInternal())).respondWith(
//            Instrumentation.ActivityResult(
//                Activity.RESULT_OK,
//                resultData
//            )
//        )

    }
}