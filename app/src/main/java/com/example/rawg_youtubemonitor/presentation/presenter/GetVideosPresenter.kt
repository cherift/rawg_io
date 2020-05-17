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
}