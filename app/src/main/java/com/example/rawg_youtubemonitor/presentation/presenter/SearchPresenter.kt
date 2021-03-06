package com.example.rawg_youtubemonitor.presentation.presenter

import com.example.rawg_youtubemonitor.data.dao.GameDao
import com.example.rawg_youtubemonitor.data.model.Game
import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.presentation.remote.GetVideoRemote
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class SearchPresenter : GetVideosContrat.Presenter<GetVideosContrat.SearchView>() {

    /**
     * Searches list of games according to the query search passed as parameter
     * in single page.
     *
     * @param search : the name of the game to search
     * @param page: the page of results where to search
     */
    fun getSearchedGames(search: String, page: Int){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        val getVideoRepo = GetVideoRemote()

        compositeDisposable.clear()

        compositeDisposable.add(getVideoRepo.getSearchedGames(search, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<GetGameResponse>() {
                override fun onSuccess(response: GetGameResponse) {
                    view?.prepareGames(response)
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }

    /**
     * Adds a game in the database.
     *
     * @param game: the game to be added
     * @param gameDao: the game DAO model
     */
    fun addGameInFavourites(game: Game, gameDao: GameDao){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(gameDao.insert(game)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {

                override fun onComplete() {
                    println("The game ${game.name} has been added to the database.")
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }


    /**
     * Gets all games saved in database.
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
                    view?.displayGames(games)
                }

                override fun onComplete() {}

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }

    /**
     * Removes a game from the database.
     *
     * @param game : the game to remove
     * @param gameDao : the the game DAO model
     */
    fun removeGame(game: Game, gameDao: GameDao) {
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(gameDao.delete(game)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {

                override fun onComplete() {
                    println("deleting game from database")
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }
}