package com.example.rawg_youtubemonitor.data.model

import com.google.gson.annotations.SerializedName

data class GetGameResponse (
    @SerializedName("results") val games: MutableList<Game>
)