package com.example.rawg_youtubemonitor.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rawg_youtubemonitor.data.model.Game
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: Game) : Completable

    @Query("SELECT * FROM games")
    fun findAll() : Flowable<MutableList<Game>>
}