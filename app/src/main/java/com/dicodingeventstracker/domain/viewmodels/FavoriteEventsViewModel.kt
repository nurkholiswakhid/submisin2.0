package com.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dicodingeventstracker.data.local.room.EventEntity
import com.dicodingeventstracker.domain.repository.FavoriteEventRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteEventsViewModel @Inject constructor(
    favoriteEventRepository: FavoriteEventRepository
) : ViewModel() {

    val showFavoriteEvents : LiveData<List<EventEntity>> = favoriteEventRepository.showFavEvent().asLiveData()
}