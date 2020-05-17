package com.example.rawg_youtubemonitor.data.model

import com.google.gson.annotations.SerializedName

data class GetVideoResponse(
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("results") val videos: MutableList<Video>
)