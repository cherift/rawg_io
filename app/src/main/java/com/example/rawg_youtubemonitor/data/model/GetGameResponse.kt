package com.example.rawg_youtubemonitor.data.model

import com.google.gson.annotations.SerializedName

data class GetGameResponse (
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("results") val games: MutableList<Game>
)