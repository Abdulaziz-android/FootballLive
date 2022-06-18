package com.abdulaziz.footballlive.models.standing

data class Standing(
    val away: List<Away>,
    val home: List<Home>,
    val total: List<Total>
)