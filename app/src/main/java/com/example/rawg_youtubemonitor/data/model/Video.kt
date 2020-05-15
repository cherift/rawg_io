package com.example.rawg_youtubemonitor.data.model

/**
 * class Video represents a Rawg video model
 */
data class Video(
    val miniatureUrl: String,
    val videoTitle: String,
    val channelName: String,
    val nbViews: Int
)