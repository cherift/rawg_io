package com.example.rawg_youtubemonitor.presentation.presenter

import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.data.model.GetVideoResponse
import com.example.rawg_youtubemonitor.presentation.remote.GetVideoRemote
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class GetVideosPresenter :  GetVideosContrat.Presenter<GetVideosContrat.GetVideosView>() {

    /**
     * Searches all games available on Rawg.io and passes the
     * result founded to the attached view
     */
    fun searchGames() {
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        val getVideoRepo = GetVideoRemote()

        compositeDisposable.clear()

        compositeDisposable.add(getVideoRepo.getAllGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<GetGameResponse>() {
                override fun onSuccess(response: GetGameResponse) {
                    view?.prepareVideos(response.games)
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }

    /**
     * Searches videos published for a game and passes the result
     * founded to the attached view.
     *
     * @param id: the id of game
     */
    fun searchGameVideos(id : String){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        val getVideoRepo = GetVideoRemote()

        compositeDisposable.clear()

        compositeDisposable.add(getVideoRepo.getGameVideos(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<GetVideoResponse>() {
                override fun onSuccess(response: GetVideoResponse) {
                    view?.displayVideos(response.videos)
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }
}