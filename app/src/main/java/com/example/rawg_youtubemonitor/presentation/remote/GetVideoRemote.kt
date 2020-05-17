package com.example.rawg_youtubemonitor.presentation.remote

import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.data.model.GetVideoResponse
import com.example.rawg_youtubemonitor.data.service.RawgService
import io.reactivex.Single

class GetVideoRemote {

    private val service : RawgService = RawgService()

    fun getAllGames(page: Int, page_size: Int) : Single<GetGameResponse> = service.getAllGames(page, page_size)

    fun getGameVideos(id: String) : Single<GetVideoResponse> = service.getGameVideos(id)

    fun getSearchedGames(search: String, page: Int) : Single<GetGameResponse> = service.getSearchedGames(search, page)
}