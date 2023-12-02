package com.bignerdranch.android.criminalintent

import java.util.Date

data class Game(
    val id: String,
    val homeTeam: String,
    val visitorTeam: String,
    val homeTeamScore: Int,
    val visitorTeamScore: Int,
    val homeConference: String,
    val visitorConference: String,
    val season: Int,
    val date: Date
)
