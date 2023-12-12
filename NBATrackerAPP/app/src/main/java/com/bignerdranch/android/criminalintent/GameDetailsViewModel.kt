package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class GameViewModel : ViewModel() {
    var gameDetails: Game? = null
        private set
    var gameId:String? = null
    fun loadGameDetails(onGameDetailsFetched: (Game) -> Unit) {
        viewModelScope.launch {
            try {
                val game = gameId?.let { Repository().fetchGameDetailsFromApi(it) }
                gameDetails = game
                if (game != null) {
                    onGameDetailsFetched(game)
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
    fun getGameID(value:String){
        gameId = value
    }
}