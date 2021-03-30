package com.johnlennonlobo.gamercolletion.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Game")
data class GameEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val nota: Float = 2.5f,
    var imagem: ByteArray? = null
):Parcelable