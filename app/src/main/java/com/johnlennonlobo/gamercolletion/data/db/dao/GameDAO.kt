package com.johnlennonlobo.gamercolletion.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.johnlennonlobo.gamercolletion.data.db.entity.GameEntity

@Dao
    interface GameDAO {

        @Insert
        suspend fun insert(gameEntity: GameEntity): Long

        @Update
        suspend fun update(gameEntity: GameEntity)

        @Query("DELETE FROM Game WHERE id = :id")
        suspend fun delete(id: Long)

        @Query("SELECT * FROM Game")
        suspend fun getAll(): List<GameEntity>

    }