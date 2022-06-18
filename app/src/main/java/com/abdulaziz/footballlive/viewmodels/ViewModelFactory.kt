package com.abdulaziz.footballlive.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdulaziz.footballlive.database.AppDatabase
import com.abdulaziz.footballlive.network.ApiClient
import com.abdulaziz.footballlive.network.NetworkHelper
import com.abdulaziz.footballlive.repositories.FootballRepository
import com.abdulaziz.footballlive.ui.league.LeagueViewModel
import com.abdulaziz.footballlive.ui.matches.current_matches.CurrentMatchViewModel
import com.abdulaziz.footballlive.ui.matches.match_details.MatchDetailsViewModel
import com.abdulaziz.footballlive.ui.standing.match.MatchViewModel
import com.abdulaziz.footballlive.ui.standing.table.TableViewModel
import com.abdulaziz.footballlive.ui.team.TeamViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val repository = FootballRepository(
            ApiClient.apiService,
            AppDatabase.getInstance(context)
        )
        val network = NetworkHelper(context)

        return if (modelClass.isAssignableFrom(CurrentMatchViewModel::class.java)) {
            CurrentMatchViewModel(repository, network) as T
        } else if (modelClass.isAssignableFrom(LeagueViewModel::class.java)) {
            LeagueViewModel(repository, network) as T
        } else if (modelClass.isAssignableFrom(TableViewModel::class.java)) {
            TableViewModel(repository, network) as T
        } else if (modelClass.isAssignableFrom(MatchDetailsViewModel::class.java)) {
            MatchDetailsViewModel(repository, network) as T
        } else if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            TeamViewModel(repository, network) as T
        } else if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            MatchViewModel(repository, network) as T
        } else throw IllegalArgumentException("Error")
    }

}