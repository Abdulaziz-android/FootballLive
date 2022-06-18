package com.abdulaziz.footballlive.models.fixtures

data class CustomEvent(
    val away_scorer: String?=null,
    val home_scorer: String?=null,
    val info_time: String?=null,
    val score: String?=null,
    val score_info: String?=null,
    val time: String?=null,

    val away_subs: Any?= null,
    val home_subs: Any?=null,

    val away_fault: String?=null,
    val card: String?=null,
    val home_fault: String?=null,
)
