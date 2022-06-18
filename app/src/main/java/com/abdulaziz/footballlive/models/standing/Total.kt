package com.abdulaziz.footballlive.models.standing

data class Total(
    val fk_stage_key: String,
    val league_key: String,
    val league_round: String,
    val league_season: String,
    val stage_name: String,
    val standing_A: String,
    val standing_D: String,
    val standing_F: String,
    val standing_GD: String,
    val standing_L: String,
    val standing_P: String,
    val standing_PTS: String,
    val standing_W: String,
    val standing_place: String,
    val standing_place_type: String,
    val standing_team: String,
    val standing_updated: String,
    val team_key: String
)