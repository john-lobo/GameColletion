package com.johnlennonlobo.gamercolletion.ui.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnlennonlobo.gamercolletion.R
import com.johnlennonlobo.gamercolletion.repository.GamerRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class GameViewModel(private val repository: GamerRepository, private val context: Context) : ViewModel() {

    private val _gameStateEnvent = MutableLiveData<GameState>()
    val gameEventData: LiveData<GameState> get() = _gameStateEnvent

    private val _messageEventData = MutableLiveData<Int>()
    val messageStateEventData: LiveData<Int> get() = _messageEventData


    fun addOrUpdateGame (id:Long, title: String, nota: Float, imagem: Bitmap? = null){
        var bitmap = imagem

        if(imagem == null) {
            bitmap = intToBitmap(R.drawable.imagem_jogo)
        }
            if(id > 0) {
                updateGame(id, title, nota, bitmap)

            }else {
                insertGame(title, nota, bitmap)
            }

    }

   private fun insertGame( title:String, nota:Float, imagem:Bitmap? = null){

       viewModelScope.launch {
           try {

             val id = repository.insertJogo(title = title, nota = nota, imagem = imagem!!)
              if(id > 0){
                   _gameStateEnvent.value = GameState.Inserted
                   _messageEventData.value = R.string.subscriber_inserted_successfully
               }
           }catch (ex :Exception){
               Log.e("erro", "erro ao inserir" + ex.toString())
               _messageEventData.value = R.string.subscriber_error_to_insert
               Log.e("erro", "erro ao inserir" + ex.toString())
           }
       }
   }

   private  fun updateGame(id:Long, title: String, nota: Float, imagem: Bitmap? = null){

        viewModelScope.launch {
            try {
                repository.updateJogo(id = id ,title = title ,nota = nota, imagem = imagem!!)
                if(id > 0){
                    _gameStateEnvent.value = GameState.Update
                    _messageEventData.value = R.string.subscriber_update_successfully
                }
            }catch (ex: Exception){
                _messageEventData.value = R.string.subscriber_error_to_update
                Log.e("erro", "erro ao atualizar" + ex.toString())
            }
        }
    }

    fun deleteSubscriber(id: Long){
        viewModelScope.launch {
            try {

                if(id > 0){
                    repository.deleteJogo(id)
                    _gameStateEnvent.value = GameState.Delete
                    _messageEventData.value = R.string.subscriber_delete_successfully
                }
            }catch (ex: Exception){
                _messageEventData.value = R.string.subscriber_error_to_delete
                Log.e("erro", "erro ao deletar" + ex.toString())
            }
        }
    }




    private fun intToBitmap(imagem: Int): Bitmap? {
        val bitmap = BitmapFactory.decodeResource(context.resources,imagem)
        return bitmap
    }


   sealed class GameState{
       object Inserted : GameState()
       object Update : GameState()
       object Delete : GameState()

   }

}