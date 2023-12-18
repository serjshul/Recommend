package com.serj.recommend.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.serj.recommend.android.ui.screens.main.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

// To use specific screens, we must also add this to manifest:
//  <activity
//        android:name=".HomeScreenActivity "
//        android:exported="false">
//  </activity>
@AndroidEntryPoint
class HomeScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HomeScreen(openScreen = { }) }
    }
}