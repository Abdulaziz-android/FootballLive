package com.abdulaziz.footballlive.models.league

import com.abdulaziz.footballlive.database.entity.League

data class Leagues(
    val result: List<League>,
    val success: Int
)