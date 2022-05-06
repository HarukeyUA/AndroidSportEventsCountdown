package com.nazar.assignment.features.sportsList.model

import com.nazar.assignment.core.domain.model.SportItem

data class SportViewItemModel(
    val id: String,
    val name: String,
    val events: List<EventViewItemModel>,
    val isExpanded: Boolean = false
)

fun SportItem.toItemModel(): SportViewItemModel {
    return SportViewItemModel(
        id = sportId,
        name = sportName,
        events = events.map { event -> event.toItemModel() }
    )
}
