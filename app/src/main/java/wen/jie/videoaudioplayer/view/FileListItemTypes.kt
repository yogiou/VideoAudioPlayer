package wen.jie.videoaudioplayer.view

class FileListItemTypes {
    interface FileListItemTypes {
        val type: ViewType
        fun copy(): FileListItemTypes
    }

    enum class ViewType(val value: Int) {
        VIDEO(0),
        AUDIO(1)
    }


}
