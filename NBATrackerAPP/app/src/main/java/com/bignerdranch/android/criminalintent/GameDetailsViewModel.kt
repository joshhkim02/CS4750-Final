package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class GameViewModel : ViewModel() {
    var gameDetails: Game? = null
        private set
    fun loadGameDetails(onGameDetailsFetched: (Game) -> Unit) {
        viewModelScope.launch {
            try {
                val game = Repository().fetchGameDetailsFromApi()
                gameDetails = game
                onGameDetailsFetched(game)
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
}