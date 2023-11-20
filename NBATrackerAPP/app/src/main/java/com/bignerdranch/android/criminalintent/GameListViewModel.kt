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
    // Exposed variable for UI to observe
    var gameDetails: Game? = null
        private set

    fun loadGameDetails(onGameDetailsFetched: (Game) -> Unit) {
        viewModelScope.launch {
            try {
                val game = fetchGameDetailsFromApi()
                onGameDetailsFetched(game)
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
    private suspend fun fetchGameDetailsFromApi(): Game {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://free-nba.p.rapidapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val trackerApi = retrofit.create(TrackerApi::class.java)

        val responseString = trackerApi.fetchGameDetails("47179")
        // Parse the JSON string into a Game object here
        val jsonObject = JSONObject(responseString)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val homeTeam = jsonObject.getJSONObject("home_team").getString("full_name")
        val visitorTeam = jsonObject.getJSONObject("visitor_team").getString("full_name")
        val homeTeamScore = jsonObject.getInt("home_team_score")
        val visitorTeamScore = jsonObject.getInt("visitor_team_score")
        val season = jsonObject.getInt("season")
        val dateString = jsonObject.getString("date")
        val date: Date = dateFormat.parse(dateString) ?: Date()

        val gameDetails = Game(
            id = jsonObject.getString("id"),
            homeTeam = homeTeam,
            visitorTeam = visitorTeam,
            homeTeamScore = homeTeamScore,
            visitorTeamScore = visitorTeamScore,
            season = season,
            date = date,
        )
        return gameDetails
    }
}