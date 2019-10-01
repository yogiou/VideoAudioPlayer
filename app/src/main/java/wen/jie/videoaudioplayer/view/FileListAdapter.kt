package wen.jie.videoaudioplayer.view

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.audio_file_list_cell.view.*
import kotlinx.android.synthetic.main.video_file_list_cell.view.*
import wen.jie.videoaudioplayer.R
import wen.jie.videoaudioplayer.model.FileDataModel

class FileListAdapter(private val adapterCallBack: AdapterCallBack) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TAG = "FileListAdapter"
    }

    interface AdapterCallBack {
        fun onAudioClick(path: String)
        fun onVideoClick(path: String)
    }

    private val mediaFileList = mutableListOf<FileDataModel>()

    fun updateFileList(fileList: List<FileDataModel>) {
        mediaFileList.clear()
        mediaFileList.addAll(fileList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FileListItemTypes.ViewType.AUDIO.value -> {
                val layout = inflater.inflate(R.layout.audio_file_list_cell, parent, false)
                AudioFileListViewHolder(layout)
            }
            else -> {
                val layout = inflater.inflate(R.layout.video_file_list_cell, parent, false)
                VideoFileListViewHolder(layout)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mediaFileList[position].isVideo) {
            return FileListItemTypes.ViewType.VIDEO.value
        }
        return FileListItemTypes.ViewType.AUDIO.value
    }

    override fun getItemCount(): Int {
        return mediaFileList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemData = mediaFileList[position]
        if (itemData.isVideo) {
            (holder as? VideoFileListViewHolder)?.bind(itemData.thumbnails, View.OnClickListener {
                adapterCallBack.onVideoClick(itemData.path)
            })
        } else {
            (holder as? AudioFileListViewHolder)?.bind(itemData.songTitle, itemData. artist, View.OnClickListener {
                adapterCallBack.onAudioClick(itemData.path)
            })
        }
    }

    class AudioFileListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(audioTitle: String?, artistString: String?, onClick: View.OnClickListener) {
            itemView.apply {
                title.text =  audioTitle
                artist.text = artistString
                setOnClickListener(onClick)
            }
        }
    }

    class VideoFileListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(thumbnailImg: Bitmap?, onClick: View.OnClickListener) {
            itemView.apply {
                thumbnailImg?.let {
                    preview.setImageBitmap(thumbnailImg)
                } ?: preview.setImageResource(R.drawable.ic_alecive_flatwoken_apps_player_video)
                setOnClickListener(onClick)
            }
        }
    }
}