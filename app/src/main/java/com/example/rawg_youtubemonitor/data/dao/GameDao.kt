package com.example.rawg_youtubemonitor.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.rawg_youtubemonitor.data.model.Game
import io.reactivex.Completable

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: Game) : Completable
}