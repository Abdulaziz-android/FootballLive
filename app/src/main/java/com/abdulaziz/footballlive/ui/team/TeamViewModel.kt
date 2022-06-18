package com.abdulaziz.footballlive.ui.team

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.footballlive.models.team.Teams
import com.abdulaziz.footballlive.network.NetworkHelper
import com.abdulaziz.footballlive.network.Resource
import com.abdulaziz.footballlive.repositories.FootballRepository
import kotlinx.coroutines.launch

class TeamViewModel(
    private val leaguesRepository: FootballRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private var liveData = MutableLiveData<Resource<Teams>>()

    fun fetchTeamDetails(team_id: String) {
        viewModelScope.launch {
            liveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val fixtures = leaguesRepository.getTeamDetails(team_id)
                if (fixtures.isSuccessful) {
                    liveData.postValue(Resource.success(fixtures.body()))
                } else liveData.postValue(Resource.error(fixtures.errorBody().toString(), null))
            } else liveData.postValue(Resource.error("Network Error!", null))
        }
    }

    fun getTeamDetails(): MutableLiveData<Resource<Teams>> {
        return liveData
    }


}