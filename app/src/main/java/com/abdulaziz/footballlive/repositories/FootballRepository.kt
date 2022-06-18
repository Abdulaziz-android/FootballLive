package com.abdulaziz.footballlive.repositories

import com.abdulaziz.footballlive.database.AppDatabase
import com.abdulaziz.footballlive.database.entity.League
import com.abdulaziz.footballlive.network.ApiService

class FootballRepository(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) {
    suspend fun getRemoteLeagues() = apiService.getLeagues()
    suspend fun getLocalLeagues() = appDatabase.leagueDao().getLeagues()
    suspend fun addLeagues(list: List<League>) = appDatabase.leagueDao().insert(list)

    suspend fun getRemoteStanding(id: String) = apiService.getStandings(league_id = id)

    suspend fun getFixtures(date:String) = apiService.getMatches(from = date, to = date)
    suspend fun getFixturesByLeagueId(date1:String,date2:String, league_id:String) = apiService.getMatchesById(from = date1, to = date2, league_id = league_id)

    suspend fun getMatchDetails(match_id:String) = apiService.getMatchDetails(match_id = match_id)

    suspend fun getTeamDetails(team_id:String) = apiService.getTeamDetails(team_id = team_id)

}