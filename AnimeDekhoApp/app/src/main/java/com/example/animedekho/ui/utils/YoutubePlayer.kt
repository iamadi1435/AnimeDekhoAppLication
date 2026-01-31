package com.example.animedekho.ui.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@SuppressLint("ContextCastToActivity")
@Composable
fun YouTubePlayerUI(
    videoUrl: String,
    modifier: Modifier = Modifier,
    onBack : () -> Unit
) {
    val activity = (LocalContext.current as? Activity) as ComponentActivity
    val videoId = remember(videoUrl) { extractYouTubeVideoId(videoUrl) }

    var player by remember { mutableStateOf<YouTubePlayer?>(null) }
    var playerView by remember { mutableStateOf<YouTubePlayerView?>(null) }

    BackHandler {
        player?.pause()
        playerView?.release()
        onBack()
    }

    DisposableEffect(Unit) {
        onDispose {
            player?.pause()
            playerView?.release()
        }
    }

        AndroidView(
            factory = { context ->
                val youTubePlayerView = YouTubePlayerView(context).apply {
                    enableAutomaticInitialization = false
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    activity.lifecycle.addObserver(this)
                    playerView = this
                }
                val options = IFramePlayerOptions.Builder()
                    .origin("https://www.youtube-nocookie.com")
                    .build()
                youTubePlayerView.initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            player = youTubePlayer
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    },
                    true,
                    options
                )
                youTubePlayerView
            },
            update = { view ->
                view.enableAutomaticInitialization = false
            },
            modifier = modifier
        )
}

fun extractYouTubeVideoId(url: String): String {
    val patterns = listOf(
        "(?<=v=)[^#&?]*",
        "(?<=be/)[^#&?]*",
        "(?<=embed/)[^#&?]*"
    )

    patterns.forEach { pattern ->
        val regex = pattern.toRegex()
        val match = regex.find(url)
        if (match != null) return match.value
    }

    return url
}