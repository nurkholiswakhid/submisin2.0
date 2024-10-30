package com.dicodingeventstracker.domain.viewmodels


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dicodingeventstracker.data.remoteUtils.RemoteResponse
import com.dicodingeventstracker.domain.repository.EventsRepository
import com.dicodingeventstracker.domain.entity.Events
import javax.inject.Inject

@HiltViewModel
class PastEventsViewmodel @Inject constructor(
    private val repository: EventsRepository,
    application: Application
): AndroidViewModel(application) {

    private val _activeValue = MutableLiveData<Int>()
    var pastResponse:MutableLiveData<RemoteResponse<List<Events>>> = MutableLiveData()

    fun setValueActive(active:Int){
        _activeValue.value =active
    }

    val pastEvent by lazy {
        _activeValue.switchMap { active ->
            repository.getPastEvents(active)
        }
        /*if (hasInternetConnection()){
            try {
                _activeValue.switchMap { active ->
                    repository.getPastEvents(active)
                }
            }catch (e: Exception){
                pastResponse.value = RemoteResponse.Error("Events not found.")
            }
        }else{
            pastResponse.value = RemoteResponse.Error("No Internet Connection.")
        }*/

    }

    private fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        )as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities= connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
            else -> false
        }
    }
}