package wen.jie.videoaudioplayer.model

import android.content.ContentResolver
import android.media.ThumbnailUtils
import android.provider.MediaStore
import io.reactivex.Observable
import kotlin.collections.ArrayList


class LoadMediaFilesService {
    fun loadAllMediaFiles(contentResolver: ContentResolver): Observable<MutableList<FileDataModel>> {
        val fileList = ArrayList<FileDataModel>()

        val audioProj = arrayOf(MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST)
        val videoProj = arrayOf(MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME)

        val audioCursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            audioProj,
            null,
            null,
            null
        )

        audioCursor?.let {
            if (audioCursor.moveToFirst()) {
                do {
                    val pathIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    val titleIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    val artistIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                    val fileDataModel = FileDataModel(audioCursor.getString(pathIndex), false, audioCursor.getString(titleIndex), audioCursor.getString(artistIndex))
                    fileList.add(fileDataModel)
                } while (audioCursor.moveToNext())
            }
        }

        audioCursor?.close()

        val videoCursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            videoProj,
            null,
            null,
            null
        )

        videoCursor?.let {
            if (videoCursor.moveToFirst()) {
                do {
                    val pathIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                    val fileDataModel = FileDataModel(videoCursor.getString(pathIndex), true, thumbnails = ThumbnailUtils.createVideoThumbnail(videoCursor.getString(pathIndex), MediaStore.Video.Thumbnails.MINI_KIND))
                    fileList.add(fileDataModel)
                } while (videoCursor.moveToNext())
            }
        }

        videoCursor?.close()

        return Observable.just(fileList)
    }
}