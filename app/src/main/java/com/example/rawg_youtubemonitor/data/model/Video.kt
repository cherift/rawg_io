package com.example.rawg_youtubemonitor.data.model

import com.google.gson.annotations.SerializedName

/**
 * class Video represents a Rawg video model
 */
data class Video(
    @SerializedName("thumbnails") val miniature: Thumbnail,
    @SerializedName("name") val videoTitle: String,
    @SerializedName("external_id") val externalId: String,
    @SerializedName("channel_title") val channelName: String,
    @SerializedName("view_count") val nbViews: Int
)

/**
 * Theses classes bellow (Image & Thumbnail) helps to serialize the video result object
 */
data class Image (
    val url: String,
    val width: Int,
    val height: Int
)

data class Thumbnail (
    val high : Image
)