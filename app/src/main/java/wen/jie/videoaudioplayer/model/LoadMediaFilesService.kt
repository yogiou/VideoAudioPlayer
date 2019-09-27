package wen.jie.videoaudioplayer.model

import android.content.ContentResolver
import java.io.File
import android.provider.MediaStore



class LoadMediaFilesService {
//    fun loadAllMediaFiles(contentResolver: ContentResolver): MutableList<File> {
//        val fileList = ArrayList<File>()
//
//        val audioProj = arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME)
//        val videoProj = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME)
//
//        val audioCursor = contentResolver.query(
//            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//            audioProj,
//            null,
//            null,
//            null
//        )
//
//        audioCursor?.let {
//            if (audioCursor.moveToFirst()) {
//                do {
////                    val audioIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.)
////
////                    fileList.add(audioCursor.getString(audioIndex))
//                } while (audioCursor.moveToNext())
//            }
//        }
//    }
}