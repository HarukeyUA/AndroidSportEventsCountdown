package com.nazar.assignment.core.domain.model

import com.nazar.assignment.core.data.response.SportItemResponse
import com.nazar.assignment.utils.loge

data class SportItem(
    val sportId: String,
    val sportName: String,
    val events: List<SportEvent>
)

fun SportItemResponse.toDomain(): SportItem? {
    if (sportId == null) {
        loge("Unable to map sport item. sportId: $sportId")
        return null
    }

    return SportItem(
        sportId = sportId,
        sportName = sportName ?: "",
        events = events?.mapNotNull { event -> event.toDomain() } ?: listOf()
    )
}
