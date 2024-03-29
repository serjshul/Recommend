package com.serj.recommend.android.tests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.serj.recommend.android.ui.screens.authentication.signIn.SignInScreen
import dagger.hilt.android.AndroidEntryPoint

// To use specific screens, we must also add this to manifest:
//  <activity
//        android:name=".HomeScreenActivity "
//        android:exported="false">
//  </activity>
@AndroidEntryPoint
class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignInScreen(
                openScreen = { },
                clearAndOpen = { }
            )
        }
    }
}