package com.abdulaziz.footballlive.ui.standing.table

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.footballlive.models.standing.Standing
import com.abdulaziz.footballlive.models.standing.Total
import com.abdulaziz.footballlive.network.NetworkHelper
import com.abdulaziz.footballlive.network.Resource
import com.abdulaziz.footballlive.repositories.FootballRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class TableViewModel(
    private val leaguesRepository: FootballRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val liveDataStandings = MutableLiveData<Resource<Standing>>()

    fun getStanding(id: String): MutableLiveData<Resource<Standing>> {
        liveDataStandings.postValue(Resource.loading(null))
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val standings = leaguesRepository.getRemoteStanding(id)
                if (standings.isSuccessful) {
                    liveDataStandings.postValue(Resource.success(standings.body()?.result))
                } else liveDataStandings.postValue(
                    Resource.error(
                        standings.errorBody().toString(),
                        null
                    )
                )
            } else liveDataStandings.postValue(Resource.error("Network Error!", null))
        }
        return liveDataStandings
    }

}