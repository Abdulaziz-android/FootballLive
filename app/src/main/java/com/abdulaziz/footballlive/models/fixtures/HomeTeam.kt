package com.abdulaziz.footballlive.models.fixtures

data class HomeTeam(
    val coaches: List<CoacheX>,
    val missing_players: List<Any>,
    val starting_lineups: List<StartingLineupX>,
    val substitutes: List<SubstituteX>
)