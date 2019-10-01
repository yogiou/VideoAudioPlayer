package wen.jie.videoaudioplayer.model

import android.graphics.Bitmap

data class FileDataModel(
    val path: String,
    val isVideo: Boolean,
    var songTitle: String? = null,
    var artist: String? = null,
    var thumbnails: Bitmap? = null
)