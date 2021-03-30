package com.johnlennonlobo.gamercolletion.ui.gameList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnlennonlobo.gamercolletion.data.db.entity.GameEntity
import com.johnlennonlobo.gamercolletion.repository.GamerRepository
import kotlinx.coroutines.launch

class GameListViewModel(
    private val repository: GamerRepository
) : ViewModel() {

    private val _allGamersEvent = MutableLiveData<List<GameEntity>>()
    val allGamersEvent: LiveData<List<GameEntity>>
        get() = _allGamersEvent

    fun getGamers() = viewModelScope.launch {
        _allGamersEvent.postValue(repository.getAllJogo())
    }
}