package com.abdulaziz.footballlive.models.team

data class Team(
    val coaches: List<Coache>,
    val players: List<Player>,
    val team_key: String,
    val team_logo: String,
    val team_name: String
)