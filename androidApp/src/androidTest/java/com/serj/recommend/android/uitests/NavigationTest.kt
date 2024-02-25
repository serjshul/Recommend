package com.serj.recommend.android.uitests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.serj.recommend.android.tests.MainScreenActivity
import org.junit.Rule

class NavigationTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainScreenActivity>()

//    @Test
//    fun app_launches() {
//        // Check app launches at the correct destination
//        Assert.assertEquals(getNavController().currentDestination?.id, R.id.nav_home)
//    }

}