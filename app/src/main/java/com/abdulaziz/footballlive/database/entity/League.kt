package com.abdulaziz.footballlive.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "leagues")
data class League(
    @PrimaryKey
    val league_key: String,
    val league_logo: String? = null,
    val league_name: String,
    val country_key: String?=null,
    val country_logo: String? = null,
    val country_name: String?=null
):Serializable