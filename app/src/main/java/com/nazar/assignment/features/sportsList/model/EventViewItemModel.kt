package com.nazar.assignment.features.sportsList.model

import com.nazar.assignment.core.domain.model.SportEvent
import java.util.*

data class EventViewItemModel(
    val id: String,
    val startTime: Date,
    val firstTeamName: String,
    val secondTeamName: String,
    val isStarred: Boolean = false
)

fun SportEvent.toItemModel(): EventViewItemModel {
    return EventViewItemModel(
        id = eventId,
        startTime = eventStartTime,
        firstTeamName = eventName.substringBefore("-").trim(),
        secondTeamName = eventName.substringAfter("-").trim()
    )
}
