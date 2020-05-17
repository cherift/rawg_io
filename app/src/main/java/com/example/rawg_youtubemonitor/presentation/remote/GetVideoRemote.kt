package com.example.rawg_youtubemonitor.presentation.remote

import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.data.model.GetVideoResponse
import com.example.rawg_youtubemonitor.data.service.RawgService
import io.reactivex.Single

class GetVideoRemote {

    private val service : RawgService = RawgService()

    fun getGameVideos(id: String, page: Int, page_size: Int) : Single<GetVideoResponse> = service.getGameVideos(id, page, page_size)

    fun getSearchedGames(search: String, page: Int) : Single<GetGameResponse> = service.getSearchedGames(search, page)
}