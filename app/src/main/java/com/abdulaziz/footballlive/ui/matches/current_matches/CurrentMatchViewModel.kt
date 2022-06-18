package com.abdulaziz.footballlive.ui.matches.current_matches

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

class CurrentMatchViewModel(
    private val leaguesRepository: FootballRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private var liveData = MutableLiveData<Resource<List<Match>>>()

    init {
        fetchFixtures()
    }

    private fun fetchFixtures() {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        viewModelScope.launch {
            liveData.postValue(Resource.loading(emptyList()))
            if (networkHelper.isNetworkConnected()) {
                val fixtures = leaguesRepository.getFixtures(date)
                if (fixtures.isSuccessful) {
                    liveData.postValue(Resource.success(fixtures.body()?.result))
                } else liveData.postValue(Resource.error(fixtures.errorBody().toString(), emptyList()))
            } else liveData.postValue(Resource.error("Network Error!", emptyList()))
        }
    }

    fun getFixtures(): MutableLiveData<Resource<List<Match>>> {
        return liveData
    }


}