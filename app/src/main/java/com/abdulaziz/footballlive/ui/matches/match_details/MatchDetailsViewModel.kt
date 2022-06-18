package com.abdulaziz.footballlive.ui.matches.match_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.footballlive.models.fixtures.Match
import com.abdulaziz.footballlive.network.NetworkHelper
import com.abdulaziz.footballlive.network.Resource
import com.abdulaziz.footballlive.repositories.FootballRepository
import kotlinx.coroutines.launch

class MatchDetailsViewModel(
    private val leaguesRepository: FootballRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private var liveData = MutableLiveData<Resource<Match>>()


    fun fetchMatchDetails(match_id: String) {

        viewModelScope.launch {
            liveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val fixtures = leaguesRepository.getMatchDetails(match_id)
                if (fixtures.isSuccessful) {
                    liveData.postValue(Resource.success(fixtures.body()?.result?.get(0)))
                } else liveData.postValue(Resource.error(fixtures.errorBody().toString(), null))
            } else liveData.postValue(Resource.error("Network Error!", null))
        }
    }

    fun getMatchDetails(): MutableLiveData<Resource<Match>> {
        return liveData
    }


}