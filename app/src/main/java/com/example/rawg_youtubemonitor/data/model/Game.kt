package com.example.rawg_youtubemonitor.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * class Game corresponding to a Game data and entity
 */
@Entity
data class Game(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "background_image") val background_image: String,
    @ColumnInfo(name = "rating") val rating : Float
)