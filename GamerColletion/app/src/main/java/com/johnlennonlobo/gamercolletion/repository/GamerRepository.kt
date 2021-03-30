package com.johnlennonlobo.gamercolletion.repository

import android.graphics.Bitmap
import com.johnlennonlobo.gamercolletion.data.db.entity.GameEntity

interface GamerRepository {

    suspend fun insertJogo(title: String, nota: Float, imagem: Bitmap) :Long

    suspend fun updateJogo(id:Long, title: String, nota: Float, imagem: Bitmap)

    suspend fun deleteJogo(id:Long)

    suspend fun getAllJogo(): List<GameEntity>
}