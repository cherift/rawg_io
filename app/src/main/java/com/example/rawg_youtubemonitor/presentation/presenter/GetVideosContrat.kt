package com.example.rawg_youtubemonitor.presentation.presenter

import android.provider.MediaStore
import com.example.rawg_youtubemonitor.data.dao.GameDao
import com.example.rawg_youtubemonitor.data.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

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

        fun searchGames()

        fun prepareVideos(games: MutableList<Game>)

        fun displayVideos(videoResponse: GetVideoResponse)

        fun readVideo(video: Video)
    }

    interface SearchView {

        fun typeOfView(): TypeOfView

        fun searchGames()

        fun addOrRemoveGame(game: Game)

        fun prepareGames(gameResponse: GetGameResponse)

        fun displayGames(games: MutableList<Game>)
    }
}