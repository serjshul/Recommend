package com.serj.recommend.android.ui.components.recommendation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.serj.recommend.android.R
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.toRepeatMode
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@Composable
fun CustomVideoPlayer(
    modifier: Modifier = Modifier
) {
    val samplePlayList = listOf(
        VideoPlayerMediaItem.RawResourceMediaItem(
            resourceId = R.raw.music_rosalia,
        )
    )

    var repeatMode by remember { mutableStateOf(RepeatMode.NONE) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height((screenWidth - 30.dp) / 16 * 9)
            .padding(bottom = 15.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        io.sanghun.compose.video.VideoPlayer(
            mediaItems = samplePlayList,
            handleLifecycle = false,
            autoPlay = false,
            usePlayerController = true,
            enablePipWhenBackPressed = false,
            enablePip = false,
            controllerConfig = VideoPlayerControllerConfig.Default.copy(
                showSubtitleButton = false,
                showNextTrackButton = false,
                showBackTrackButton = false,
                showBackwardIncrementButton = false,
                showForwardIncrementButton = false,
                showRepeatModeButton = false,
                showFullScreenButton = true,
                controllerAutoShow = false
            ),
            repeatMode = repeatMode,
            onCurrentTimeChanged = {
                Log.e("CurrentTime", it.toString())
            },
            playerInstance = {
                Log.e("VOLUME", volume.toString())
                addAnalyticsListener(object : AnalyticsListener {
                    @SuppressLint("UnsafeOptInUsageError")
                    override fun onRepeatModeChanged(
                        eventTime: AnalyticsListener.EventTime,
                        rMode: Int,
                    ) {
                        repeatMode = rMode.toRepeatMode()
                    }
                })
            },
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
        )
    }
}