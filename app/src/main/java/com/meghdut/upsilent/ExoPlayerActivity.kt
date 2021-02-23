package com.meghdut.upsilent

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ExoPlayerActivity : AppCompatActivity() {
    //Media URL
    private val videoURIMKV = Uri.parse("https://kiit-drive.potassium.workers.dev/0:/Breaking%20Bad%20Seasons%201%20to%205/Breaking%20Bad%20Season%202/%5BS02.E02%5D%20Breaking%20Bad%20-%20Grilled.mp4")
    private lateinit var buttonTrackSelector: Button
    private lateinit var buttonMenu: ImageView
    private lateinit var buttonSpeedControl: ImageView
    private lateinit var buttonFullScreen: ImageView
    private lateinit var textSpeed: TextView
    private var screenOrientationFlag = false

    //ExoPlayer Declarations
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var playerView: PlayerView? = null
    private var defaultTrackSelector: DefaultTrackSelector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)
        playerView = findViewById<View>(R.id.player_view) as PlayerView
        buttonMenu = playerView!!.findViewById(R.id.button_menu)
        //        buttonSpeedControl = playerView.findViewById(R.id.button_speed_control);
        buttonFullScreen = playerView!!.findViewById(R.id.button_fullscreen)
        textSpeed = playerView!!.findViewById(R.id.text_speed)
        textSpeed.setOnClickListener(View.OnClickListener { v: View? ->
            val qualityRange = arrayOf(
                    "0.25x", "0.5x", "0.75x", "1x", "1.25x", "1.5x", "2x"
            )
            val builder = AlertDialog.Builder(this@ExoPlayerActivity)
            builder.setTitle("Playback Speed")
            builder.setItems(qualityRange) { dialog: DialogInterface?, which: Int ->
                if ("0.25x" == qualityRange[which]) {
                    val param = PlaybackParameters(0.25f)
                    simpleExoPlayer!!.setPlaybackParameters(param)
                    textSpeed.setText("0.25x")
                }
                if ("0.5x" == qualityRange[which]) {
                    val param = PlaybackParameters(0.5f)
                    simpleExoPlayer!!.setPlaybackParameters(param)
                    textSpeed.setText("0.5x")
                } else if ("0.75x" == qualityRange[which]) {
                    val param = PlaybackParameters(0.75f)
                    simpleExoPlayer!!.setPlaybackParameters(param)
                    textSpeed.setText("0.75x")
                }
                if ("1x" == qualityRange[which]) {
                    val param = PlaybackParameters(1.0f)
                    simpleExoPlayer!!.setPlaybackParameters(param)
                    textSpeed.setText("1x")
                } else if ("1.25x" == qualityRange[which]) {
                    val param = PlaybackParameters(1.25f)
                    simpleExoPlayer!!.setPlaybackParameters(param)
                    textSpeed.setText("1.25x")
                } else if ("1.5x" == qualityRange[which]) {
                    val param = PlaybackParameters(1.5f)
                    simpleExoPlayer!!.setPlaybackParameters(param)
                    textSpeed.setText("1.5x")
                } else if ("1.75x" == qualityRange[which]) {
                    val param = PlaybackParameters(1.75f)
                    simpleExoPlayer!!.setPlaybackParameters(param)
                    textSpeed.setText("1.75x")
                } else if ("2x" == qualityRange[which]) {
                    val param = PlaybackParameters(2.0f)
                    simpleExoPlayer!!.setPlaybackParameters(param)
                    textSpeed.setText("2x")
                }
            }
            builder.show()
        })
        buttonMenu.setOnClickListener(View.OnClickListener { v: View? -> showPopup(v) })
        buttonFullScreen.setOnClickListener(View.OnClickListener { v: View? ->
            if (screenOrientationFlag) {
                buttonFullScreen.setImageDrawable(getDrawable(R.drawable.ic_fullscreen))
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                screenOrientationFlag = false
            } else {
                buttonFullScreen.setImageDrawable(getDrawable(R.drawable.ic_fullscreen_exit))
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                screenOrientationFlag = true
            }
        })
    }

    private fun showPopup(v: View?) {
        val popup = PopupMenu(this, v)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.actions, popup.menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.quality -> trackSelectionDialog()
            }
            true
        }
        popup.show()
    }

    private fun initializePlayer() {
        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory(Util.getUserAgent(this, "UpSilent"))
        // Create a progressive media source pointing to a stream uri.
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoURIMKV)

        // Default Track Selector
        defaultTrackSelector = DefaultTrackSelector(this)
        simpleExoPlayer = SimpleExoPlayer.Builder(this)
                .setTrackSelector(defaultTrackSelector!!)
                .build()

        // Bind the player to the view.
        playerView!!.player = simpleExoPlayer
        simpleExoPlayer!!.prepare(mediaSource)
        simpleExoPlayer!!.playWhenReady = true
    }

    private fun trackSelectionDialog() {
        val mappedTrackInfo = defaultTrackSelector!!.currentMappedTrackInfo
        if (mappedTrackInfo != null) {
            val rendererIndex = 0
            val rendererType = mappedTrackInfo.getRendererType(rendererIndex)
            val allowAdaptiveSelections = (rendererType == C.TRACK_TYPE_VIDEO
                    || (rendererType == C.TRACK_TYPE_AUDIO
                    && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                    == MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS))
            val build = TrackSelectionDialogBuilder(this@ExoPlayerActivity, "Track Selector", defaultTrackSelector!!, rendererIndex)
            build.setAllowAdaptiveSelections(allowAdaptiveSelections)
            build.build().show()
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
            if (playerView != null) {
                playerView!!.onResume()
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || simpleExoPlayer == null) {
            initializePlayer()
            if (playerView != null) {
                playerView!!.onResume()
            }
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView!!.onPause()
            }
            simpleExoPlayer!!.release()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView!!.onPause()
            }
            simpleExoPlayer!!.release()
        }
    }
}