package com.johnlennonlobo.gamercolletion.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.johnlennonlobo.gamercolletion.data.db.dao.GameDAO
import com.johnlennonlobo.gamercolletion.data.db.entity.GameEntity

@Database(entities = [GameEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract val gamerDAO: GameDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance (context: Context): AppDataBase {
            synchronized(this){
                var instance: AppDataBase? = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context, AppDataBase::class.java,
                        "app_database_game"
                    ).build()
                }
                return instance
            }

        }
    }

}