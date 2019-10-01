package wen.jie.videoaudioplayer.viewmodel

import android.content.ContentResolver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import wen.jie.videoaudioplayer.model.FileDataModel
import wen.jie.videoaudioplayer.model.LoadMediaFilesService
import wen.jie.videoaudioplayer.utils.RxBus
import wen.jie.videoaudioplayer.utils.RxEvent

class FileListViewModel {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val loadMediaFilesService: LoadMediaFilesService = LoadMediaFilesService()

    class DisplayOnShowLoading(val isVisible: Boolean) : RxEvent
    class DisplayOnShowFileList(val fileList: List<FileDataModel>) : RxEvent

    fun onCleared() {
        compositeDisposable.clear()
    }

    fun loadAllMediaFiles(contentResolver: ContentResolver) {
        loadMediaFilesService.loadAllMediaFiles(contentResolver)
            .doOnSubscribe {
                RxBus.getInstance().send(DisplayOnShowLoading(true))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                RxBus.getInstance().send(DisplayOnShowLoading(false))
            }
            .subscribeBy (
                onNext = { fileList ->
                    RxBus.getInstance().send(DisplayOnShowFileList(fileList))
                },
                onError = { error ->

                }
            ).addTo(compositeDisposable)
    }
}