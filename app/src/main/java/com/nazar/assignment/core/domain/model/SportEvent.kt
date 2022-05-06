package com.nazar.assignment.core.domain.model

import com.nazar.assignment.core.data.response.EventItemResponse
import com.nazar.assignment.utils.loge
import java.util.*
import java.util.concurrent.TimeUnit

data class SportEvent(
    val eventId: String,
    val sportId: String,
    val eventName: String,
    val eventStartTime: Date
)

fun EventItemResponse.toDomain(): SportEvent? {
    if (eventId == null || sportId == null) {
        loge("Unable to map sport event. eventId: $eventId sportId: $sportId")
        return null
    }

    val eventMillisecondsStart =
        TimeUnit.MILLISECONDS.convert(eventStartTime ?: 0, TimeUnit.SECONDS)
    return SportEvent(
        eventId = eventId,
        sportId = sportId,
        eventName = eventName ?: "",
        eventStartTime = Date(eventMillisecondsStart)
    )
}
