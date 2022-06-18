package com.abdulaziz.footballlive.ui.league

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.footballlive.database.entity.League
import com.abdulaziz.footballlive.network.NetworkHelper
import com.abdulaziz.footballlive.network.Resource
import com.abdulaziz.footballlive.repositories.FootballRepository
import kotlinx.coroutines.launch

class LeagueViewModel(
    private val leaguesRepository: FootballRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val liveDataLeagues = MutableLiveData<Resource<List<League>>>()

    init {
        fetchLeagues()
    }

    private fun fetchLeagues(){
        viewModelScope.launch {
            liveDataLeagues.postValue(Resource.loading(emptyList()))
            if (networkHelper.isNetworkConnected()) {
                val standings = leaguesRepository.getRemoteLeagues()
                if (standings.isSuccessful) {
                    leaguesRepository.addLeagues(standings.body()?.result!!)
                    liveDataLeagues.postValue(Resource.success(standings.body()?.result))
                } else liveDataLeagues.postValue(Resource.error(standings.message().toString(), emptyList()))
            }else {
                val localData = leaguesRepository.getLocalLeagues()
                if (localData.isNotEmpty()) {
                    liveDataLeagues.postValue(Resource.success(localData))
                } else liveDataLeagues.postValue(Resource.error("Network Error!", emptyList()))
            }
        }
    }

   fun getLeagues(): MutableLiveData<Resource<List<League>>> {
       return liveDataLeagues
   }

}