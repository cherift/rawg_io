package com.example.rawg_youtubemonitor.data.dao

import androidx.room.*
import com.example.rawg_youtubemonitor.data.model.Game
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: Game) : Completable

    @Query("SELECT * FROM games")
    fun findAll() : Flowable<MutableList<Game>>

    @Delete
    fun delete(game: Game) : Completable
}