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

        // attribute to contain favourite games
        var favouriteGames : MutableList<Game> = mutableListOf<Game>()

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

        /**
         * Gets all games saved in database and adds it to
         * the favouriteGames attribute.
         *
         * @param gameDao : the game DAO model
         */
        fun getFavouriteGames(gameDao: GameDao){
            val compositeDisposable : CompositeDisposable = CompositeDisposable()

            compositeDisposable.clear()

            compositeDisposable.add(gameDao.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ResourceSubscriber<MutableList<Game>>() {

                    override fun onNext(games : MutableList<Game>) {
                        favouriteGames.addAll(games)
                    }

                    override fun onComplete() {}

                    override fun onError(e: Throwable) {
                        println(e.message)
                    }
                })
            )
        }

    }

    interface GetVideosView {

        fun searchGames()

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