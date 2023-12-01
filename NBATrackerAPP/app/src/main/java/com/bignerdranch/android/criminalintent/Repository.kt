package com.bignerdranch.android.criminalintent

import com.bignerdranch.android.criminalintent.api.TrackerApi
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Repository {
    var games: List<Game>? = null
        private set

    private suspend fun fetchGamesFromApi(): List<Game> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://free-nba.p.rapidapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val trackerApi = retrofit.create(TrackerApi::class.java)

        val responseString = trackerApi.fetchGames(page = 0, perPage = 5, date = "2018-04-06")
        val jsonResponse = JSONObject(responseString)
        val gamesArray = jsonResponse.getJSONArray("data")
        val gamesList = mutableListOf<Game>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        for (i in 0 until gamesArray.length()) {
            val gameJson = gamesArray.getJSONObject(i)
            val homeTeam = gameJson.getJSONObject("home_team").getString("full_name")
            val visitorTeam = gameJson.getJSONObject("visitor_team").getString("full_name")
            val homeTeamScore = gameJson.getInt("home_team_score")
            val visitorTeamScore = gameJson.getInt("visitor_team_score")
            val season = gameJson.getInt("season")
            val dateString = gameJson.getString("date")
            val date: Date = dateFormat.parse(dateString) ?: Date()

            val game = Game(
                id = gameJson.getString("id"),
                homeTeam = homeTeam,
                visitorTeam = visitorTeam,
                homeTeamScore = homeTeamScore,
                visitorTeamScore = visitorTeamScore,
                season = season,
                date = date
            )

            gamesList.add(game)
        }

        return gamesList
    }
}