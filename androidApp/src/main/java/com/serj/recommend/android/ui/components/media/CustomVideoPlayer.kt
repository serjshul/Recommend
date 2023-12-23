package com.serj.recommend.android.ui.components.media

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomVideoPlayer(
    modifier: Modifier = Modifier
) {
    /*
    val samplePlayList = listOf()
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

     */
}