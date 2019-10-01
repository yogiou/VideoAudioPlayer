package wen.jie.videoaudioplayer.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_player.*
import wen.jie.videoaudioplayer.R
import java.io.File

class PlayerFragment : Fragment() {
    companion object {
        const val TAG = "PlayerFragment"
        const val PATH_KEY = "PATH_KEY"
        const val POSITION_KEY = "CURRENT_POSITION_KEY"
    }

    private var path: String? = null
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var mResumePosition = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private val adaptiveTrackSelectionFactory by lazy {
        AdaptiveTrackSelection.Factory()
    }

    private fun init() {
        arguments?.let {
            path = it.getString(PATH_KEY)
            mResumePosition = it.getLong(POSITION_KEY, 0)
            initializeExoplayer(path)
        }
    }

    private fun initializeExoplayer(path: String?) {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
            context,
            DefaultRenderersFactory(context),
            DefaultTrackSelector(adaptiveTrackSelectionFactory),
            DefaultLoadControl()
        )

        exo_play.apply {
            player = simpleExoplayer
        }

        simpleExoplayer.seekToDefaultPosition()
        simpleExoplayer.prepare(buildMediaSource(Uri.fromFile(File(path))))
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(playerEventListener)
    }

    private fun buildMediaSource(uri: Uri) : MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, TAG))
        return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }

    override fun onResume() {
        simpleExoplayer.seekTo(mResumePosition)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        pauseExoplayer()
    }

    override fun onDestroy() {
        releaseExoplayer()
        super.onDestroy()
    }

    private fun pauseExoplayer() {
        simpleExoplayer.playWhenReady = false
        mResumePosition = simpleExoplayer.currentPosition
    }

    private fun releaseExoplayer() {
        simpleExoplayer.release()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    private val playerEventListener by lazy {
        PlayerEventListener()
    }

    inner class PlayerEventListener : Player.EventListener {
        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

        }

        override fun onSeekProcessed() {

        }

        override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {

        }

        override fun onPlayerError(error: ExoPlaybackException?) {

        }

        override fun onLoadingChanged(isLoading: Boolean) {

        }

        override fun onPositionDiscontinuity(reason: Int) {
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

        }
    }
}