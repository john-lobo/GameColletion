package com.johnlennonlobo.gamercolletion.repository

import android.graphics.Bitmap
import com.johnlennonlobo.gamercolletion.data.db.dao.GameDAO
import com.johnlennonlobo.gamercolletion.data.db.entity.GameEntity
import java.io.ByteArrayOutputStream

class DatabaseDataSource(private val gameDAO: GameDAO) : GamerRepository{


    override suspend fun insertJogo(title: String, nota: Float, imagem: Bitmap): Long {

        val gameEntity = GameEntity(
            title = title,
            nota = nota,
            imagem = bitmapToByteArray(imagem)
        )
        return gameDAO.insert(gameEntity)
    }

    override suspend fun updateJogo(id: Long, title: String, nota: Float, imagem: Bitmap) {

        val gameEntity = GameEntity(
            id = id,
            title = title,
            nota = nota,
            imagem = bitmapToByteArray(imagem)
        )
        gameDAO.update(gameEntity)
    }

    override suspend fun deleteJogo(id: Long) {
     gameDAO.delete(id)
    }

    override suspend fun getAllJogo(): List<GameEntity> {
     return gameDAO.getAll()
    }

    private fun bitmapToByteArray(imagem: Bitmap): ByteArray {
        val byteArray=ByteArrayOutputStream()
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
        val imagemB=byteArray.toByteArray()
        return imagemB
    }
}