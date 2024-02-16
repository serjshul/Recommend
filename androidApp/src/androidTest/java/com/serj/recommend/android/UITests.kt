package com.serj.recommend.android

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serj.recommend.android.tests.MainScreenActivity
import com.serj.recommend.android.ui.screens.authentication.signIn.AUTHENTICATION_EMAIL_FIELD_TT
import com.serj.recommend.android.ui.screens.authentication.signIn.AUTHENTICATION_PASSWORD_FIELD_TT
import com.serj.recommend.android.ui.screens.authentication.signIn.AUTHENTICATION_SIGN_IN_BUTTON_TT
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


val email = "daxavic@yandex.ru"
val password = "123Qwerty"

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
            waitUntilNodeCount(hasText("Email"), 1, 3000)
            onNodeWithTag(AUTHENTICATION_EMAIL_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(email)
            onNodeWithTag(AUTHENTICATION_PASSWORD_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(password)
            onNodeWithTag(AUTHENTICATION_SIGN_IN_BUTTON_TT)
                .performClick()
           waitUntilNodeCount(hasText("Read"), 1, 20000)
            onNodeWithText("Read")
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun makeAuthenticationEmptyPassword() {
        rule.apply {
            waitUntilNodeCount(hasText("Email"), 1, 3000)
            onNodeWithTag(AUTHENTICATION_EMAIL_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(email)
            onNodeWithTag(AUTHENTICATION_SIGN_IN_BUTTON_TT)
                .performClick()
            onNodeWithText(rule.activity.getString(R.string.empty_password_error))
            //onNodeWithText("Password cannot be empty")
                .assertIsDisplayed()
        }
    }


    //failed test:(
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun makeAuthenticationIncorrectPassword() {
        val error = "The supplied auth credential is incorrect, malformed or has expired."
        rule.apply {
            waitUntilNodeCount(hasText("Email"), 1, 3000)
            onNodeWithTag(AUTHENTICATION_EMAIL_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput(email)
            onNodeWithTag(AUTHENTICATION_PASSWORD_FIELD_TT)
                .assertIsDisplayed()
                .performTextInput("111")
            onNodeWithTag(AUTHENTICATION_SIGN_IN_BUTTON_TT)
                .performClick()
            waitUntilNodeCount(hasText("Error"), 1, 20000)
            onNode(
                hasText(rule.activity.getString(R.string.error_sign_in))
                        or hasText(error))
                .assertIsDisplayed()
        }
    }




    @OptIn(ExperimentalTestApi::class)
    @Test
    fun openHomeScreen() {
        rule.setContent { MainScreenActivity() }
        rule.apply{
            waitUntilNodeCount(hasText("Read"), 1, 20000)
            onNodeWithText("Read")
                .assertIsDisplayed()

        }
//         val nodeWithReadText =
//            rule.onNodeWithText("Read")
//
//        rule.mainClock.advanceTimeUntil(3_000) {
//            1 == 1
//        }
//
//        nodeWithReadText.assertExists()

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