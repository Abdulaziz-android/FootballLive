package com.abdulaziz.footballlive.models.fixtures

data class Substitutes(
    val away_scorer: List<AwayScorer>?= null,
    val home_scorer: List<HomeScorer>?=null,
    val info_time: String,
    val score: String,
    val time: String
)