package wen.jie.videoaudioplayer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_file_list.*
import wen.jie.videoaudioplayer.R
import wen.jie.videoaudioplayer.model.FileDataModel
import wen.jie.videoaudioplayer.utils.RxBus
import wen.jie.videoaudioplayer.viewmodel.FileListViewModel

class FileListFragment(private val adapter: FileListFragmentAdapter) : Fragment(), FileListAdapter.AdapterCallBack {
    private val fileListViewModel = FileListViewModel()
    private val compositeDisposable by lazy { CompositeDisposable() }

    interface FileListFragmentAdapter {
        fun goToAudioPlayerFragment(path: String)
        fun goToVideoPlayerFragment(path: String)
    }

    companion object {
        const val TAG = "FileListFragment"
    }

    private fun init() {
        fileList.layoutManager = LinearLayoutManager(context)
    }

    private fun setAdapter(list: List<FileDataModel>) {
        val adapter = FileListAdapter(this)
        adapter.updateFileList(list)
        fileList.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_file_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        initSubscription()
        context?.let {
            fileListViewModel.loadAllMediaFiles(it.contentResolver)
        }
    }

    override fun onPause() {
        super.onPause()
        fileListViewModel.onCleared()
        clearSubscriptions()
    }

    private fun initSubscription() {
        RxBus.getInstance().toObservable()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { rxEvent ->
                when (rxEvent) {
                    is FileListViewModel.DisplayOnShowFileList -> {
                        setAdapter(rxEvent.fileList)
                    }
                    is FileListViewModel.DisplayOnShowLoading -> {
                        showLoading(rxEvent.isVisible)
                    }
                }
            }
            .addTo(compositeDisposable)
    }

    private fun clearSubscriptions() {
        compositeDisposable.clear()
    }

    private fun showLoading(isVisible: Boolean) {
        if (isVisible) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    override fun onAudioClick(path: String) {
        adapter.goToAudioPlayerFragment(path)
    }

    override fun onVideoClick(path: String) {
        adapter.goToVideoPlayerFragment(path)
    }
}