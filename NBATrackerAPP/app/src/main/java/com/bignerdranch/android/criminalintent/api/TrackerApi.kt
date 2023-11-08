package com.bignerdranch.android.criminalintent.api

import retrofit2.http.GET

interface TrackerApi {
    @GET("/")
    suspend fun fetchContents(): String
}