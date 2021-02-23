package com.meghdut.upsilent

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener
import com.google.android.youtube.player.YouTubePlayerView
import com.meghdut.upsilent.network.URLConstants

class YouTubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private var key: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_you_tube_acitivity)
        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.youTubePlayer)
        youTubePlayerView.initialize(URLConstants.YOUTUBE_VIDEO_PLAYER_API_KEY, this)
        val intent = intent
        key = intent.getStringExtra("VIDEO_KEY")
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, b: Boolean) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer.setPlaybackEventListener(playbackEventListener)
        if (!b) {
            youTubePlayer.cueVideo(key)
        }
    }

    private val playbackEventListener: PlaybackEventListener = object : PlaybackEventListener {
        override fun onPlaying() {}
        override fun onPaused() {}
        override fun onStopped() {}
        override fun onBuffering(b: Boolean) {}
        override fun onSeekTo(i: Int) {}
    }
    private val playerStateChangeListener: PlayerStateChangeListener = object : PlayerStateChangeListener {
        override fun onLoading() {}
        override fun onLoaded(s: String) {}
        override fun onAdStarted() {}
        override fun onVideoStarted() {}
        override fun onVideoEnded() {}
        override fun onError(errorReason: YouTubePlayer.ErrorReason) {}
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {}
}