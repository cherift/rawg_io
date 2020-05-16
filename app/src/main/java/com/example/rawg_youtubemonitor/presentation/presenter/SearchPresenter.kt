package com.example.rawg_youtubemonitor.presentation.presenter

import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.presentation.remote.GetVideoRemote
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter : GetVideosContrat.Presenter<GetVideosContrat.SearchView>() {


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
}