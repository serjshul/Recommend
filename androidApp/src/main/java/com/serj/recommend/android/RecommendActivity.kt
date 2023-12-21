package com.serj.recommend.android

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.AndroidEntryPoint

const val APP_TAG = "Recommend."
private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

@AndroidEntryPoint
@ExperimentalMaterialApi
class RecommendActivity : ComponentActivity() {
    private val connectedToastMessage by lazy {
        getString(R.string.goes_online_toast)
    }
    private val disconnectedToastMessage by lazy {
        getString(R.string.goes_offline_toast)
    }

    private lateinit var connectivityManager: ConnectivityManager

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()

    private val networkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(net: Network) {
                super.onAvailable(net)
                Toast.makeText(
                    applicationContext, connectedToastMessage, Toast.LENGTH_SHORT
                ).show()
                // for example in app use like that:
//            viewModel.isWiFiAvailable = true
            }

            override fun onLost(net: Network) {
                super.onLost(net)
                Toast.makeText(
                    applicationContext, disconnectedToastMessage, Toast.LENGTH_SHORT
                ).show()
//            viewModel.isWiFiAvailable = false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecommendApp()
        }
        bindAndRequestConnectivityManager()
    }

    private fun bindAndRequestConnectivityManager() {
        connectivityManager = getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        connectivityManager.requestNetwork(
            networkRequest, networkCallback
        )
    }
}
