package com.example.rawg_youtubemonitor.presentation.presenter

import android.provider.MediaStore
import com.example.rawg_youtubemonitor.data.model.Game
import com.example.rawg_youtubemonitor.data.model.Video
import io.reactivex.disposables.CompositeDisposable

/**
 * Class GetVideosContrat used to respect Google Clean Architecture
 */
class GetVideosContrat {

    abstract class Presenter<V> {

        var view : V? = null
        var compositeDisposable : CompositeDisposable? = null

        init {
            compositeDisposable = CompositeDisposable()
        }

        /**
         * Attaches view in the presenter.
         */
        fun attachView(view: V) {
            this.view = view
        }

        /**
         * Clears the composite disposable.
         */
        fun cancelSubscription() {
            compositeDisposable!!.clear()
        }

        /**
         * Makes the composite disposable disposing.
         */
        fun detachView() {
            compositeDisposable!!.dispose()
            view = null
        }

    }

    interface GetVideosView {

        fun prepareVideos(games: MutableList<Game>)

        fun displayVideos(videos: MutableList<Video>)

        fun readVideo(video: Video)
    }
}