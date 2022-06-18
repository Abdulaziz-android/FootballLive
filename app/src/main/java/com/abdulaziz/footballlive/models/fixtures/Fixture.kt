package com.abdulaziz.footballlive.models.fixtures

data class Fixture(
    val league_id:String,
    val league_name:String,
    val league_image_url:String,
    val list: List<Match>
)
