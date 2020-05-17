package com.example.rawg_youtubemonitor.presentation.presenter

import com.example.rawg_youtubemonitor.data.dao.GameDao
import com.example.rawg_youtubemonitor.data.model.Game
import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.data.model.GetVideoResponse
import com.example.rawg_youtubemonitor.presentation.remote.GetVideoRemote
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subscribers.ResourceSubscriber

class GetVideosPresenter :  GetVideosContrat.Presenter<GetVideosContrat.GetVideosView>() {

    /**
     * Searches videos published for a game and passes the result
     * founded to the attached view.
     *
     * Result founded corresponds only to a page passed as parameter.
     *
     * @param id: the id of game
     * @param page: the page number within the paginated result set.
     * @param page_size: the number of results to return per page.
     *                   By default, this value is 50.
     */
    fun searchGameVideos(id : String, page: Int, page_size: Int = 50){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        val getVideoRepo = GetVideoRemote()

        compositeDisposable.clear()

        compositeDisposable.add(getVideoRepo.getGameVideos(id, page, page_size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<GetVideoResponse>() {
                override fun onSuccess(response: GetVideoResponse) {
                    view?.displayVideos(response)
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
                    view?.prepareVideos(games)
                }

                override fun onComplete() {}

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }
}