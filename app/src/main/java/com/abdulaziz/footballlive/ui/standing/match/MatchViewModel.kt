package com.abdulaziz.footballlive.ui.standing.match

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.footballlive.models.fixtures.Match
import com.abdulaziz.footballlive.network.NetworkHelper
import com.abdulaziz.footballlive.network.Resource
import com.abdulaziz.footballlive.repositories.FootballRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MatchViewModel(
    private val leaguesRepository: FootballRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private var liveData = MutableLiveData<Resource<List<Match>>>()


    fun fetchFixtures(league_id: String) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val instance = Calendar.getInstance()
        instance.add(Calendar.DAY_OF_MONTH, -1)
        val yesterday = instance.time
        instance.add(Calendar.DAY_OF_MONTH, 2)
        val tomorrow = instance.time
        val fromDate = format.format(yesterday)
        val toDate = format.format(tomorrow)

        viewModelScope.launch {
            liveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val fixtures = leaguesRepository.getFixturesByLeagueId(fromDate, toDate, league_id)
                if (fixtures.isSuccessful) {
                    liveData.postValue(Resource.success(fixtures.body()?.result))
                } else liveData.postValue(Resource.error(fixtures.errorBody().toString(), null))
            } else liveData.postValue(Resource.error("Network Error!", null))
        }
    }

    fun getFixtures(): MutableLiveData<Resource<List<Match>>> {
        return liveData
    }


}