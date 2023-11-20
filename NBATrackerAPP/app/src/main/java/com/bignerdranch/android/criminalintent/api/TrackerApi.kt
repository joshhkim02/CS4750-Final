package com.bignerdranch.android.criminalintent.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface TrackerApi {
    @GET("games/{gameId}")
    @Headers(
        "X-RapidAPI-Key: f619bc0200msh31285ed4ef36b7fp1e1b5bjsn9abb4061315a",
        "X-RapidAPI-Host: free-nba.p.rapidapi.com"
    )
    suspend fun fetchGameDetails(@Path("gameId") gameId: String): String
}