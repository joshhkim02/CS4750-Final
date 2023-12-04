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
    private val trackerApi: TrackerApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://free-nba.p.rapidapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        trackerApi = retrofit.create(TrackerApi::class.java)
    }

    suspend fun fetchGamesFromApi(): List<Game> {
        val responseString = trackerApi.fetchGames(page = 0, perPage = 25, date = "2018-04-06")
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
            val homeConference = gameJson.getJSONObject("home_team").getString("conference")
            val visitorConference = gameJson.getJSONObject("visitor_team").getString("conference")
            val homeCity = gameJson.getJSONObject("home_team").getString("city")

            val game = Game(
                id = gameJson.getString("id"),
                homeTeam = homeTeam,
                visitorTeam = visitorTeam,
                homeTeamScore = homeTeamScore,
                visitorTeamScore = visitorTeamScore,
                season = season,
                date = date,
                visitorConference = visitorConference,
                homeCity = homeCity,
                homeConference = homeConference
            )

            gamesList.add(game)
        }

        return gamesList
    }

    suspend fun fetchGameDetailsFromApi(): Game {
        val responseString = trackerApi.fetchGameDetails("48747")
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
        val homeConference = jsonObject.getJSONObject("home_team").getString("conference")
        val visitorConference = jsonObject.getJSONObject("visitor_team").getString("conference")
        val homeCity = jsonObject.getJSONObject("home_team").getString("city")


        val gameDetails = Game(
            id = jsonObject.getString("id"),
            homeTeam = homeTeam,
            visitorTeam = visitorTeam,
            homeTeamScore = homeTeamScore,
            visitorTeamScore = visitorTeamScore,
            season = season,
            date = date,
            visitorConference = visitorConference,
            homeConference = homeConference,
            homeCity = homeCity
        )
        return gameDetails
    }

}