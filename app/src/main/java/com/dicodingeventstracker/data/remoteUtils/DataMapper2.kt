package com.dicodingeventstracker.data.remoteUtils

import com.dicodingeventstracker.data.remote.response.Event
import com.dicodingeventstracker.data.remote.response.EventsItem
import com.dicodingeventstracker.data.remote.response.ResponseEvents
import com.dicodingeventstracker.domain.entity.Events

fun List<EventsItem>.toDomainEvents() = map {
    EventsItem(it.summary,
        it.mediaCover,
        it.registrants,
        it.imageLogo,
        it.link,
        it.description,
        it.ownerName,
        it.cityName,
        it.quota,it.name,
        it.id,
        it.category,
        it.beginTime,
        it.endTime)
}