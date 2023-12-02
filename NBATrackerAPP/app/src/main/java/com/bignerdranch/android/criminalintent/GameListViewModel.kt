package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.criminalintent.api.TrackerApi
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


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