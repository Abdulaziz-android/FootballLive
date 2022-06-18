package com.abdulaziz.footballlive.network

import com.abdulaziz.footballlive.models.fixtures.Fixtures
import com.abdulaziz.footballlive.models.league.Leagues
import com.abdulaziz.footballlive.models.standing.Standings
import com.abdulaziz.footballlive.models.team.Teams
import com.abdulaziz.footballlive.network.ApiClient.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("football")
    suspend fun getLeagues(
        @Query("APIkey") apiKey:String = API_KEY,
        @Query("met") met:String = "Leagues",
    ):Response<Leagues>

    @GET("football")
    suspend fun getStandings(
        @Query("APIkey") apiKey:String = API_KEY,
        @Query("met") met:String = "Standings",
        @Query("leagueId") league_id:String,
    ): Response<Standings>

    @GET("football")
    suspend fun getMatches(
        @Query("APIkey") apiKey:String = API_KEY,
        @Query("met") met:String = "Fixtures",
        @Query("from") from:String,
        @Query("to") to:String,
        @Query("timezone") timezone:String = "Asia/Tashkent",
    ): Response<Fixtures>

    @GET("football")
    suspend fun getMatchDetails(
        @Query("APIkey") apiKey:String = API_KEY,
        @Query("met") met:String = "Fixtures",
        @Query("matchId") match_id:String,
        @Query("timezone") timezone:String = "Asia/Tashkent",
    ): Response<Fixtures>

    @GET("football")
    suspend fun getMatchesById(
        @Query("APIkey") apiKey:String = API_KEY,
        @Query("met") met:String = "Fixtures",
        @Query("from") from:String,
        @Query("to") to:String,
        @Query("leagueId") league_id:String,
        @Query("timezone") timezone:String = "Asia/Tashkent",
    ): Response<Fixtures>

    @GET("football")
    suspend fun getTeamDetails(
        @Query("APIkey") apiKey:String = API_KEY,
        @Query("met") met:String = "Teams",
        @Query("teamId") team_id:String
    ): Response<Teams>

}