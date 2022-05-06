package com.nazar.assignment.features.sportsList.model

import androidx.annotation.DrawableRes
import com.nazar.assignment.core.domain.model.SportItem

data class SportViewItemModel(
    val id: String,
    val name: String,
    val events: List<EventViewItemModel>,
    @DrawableRes val icon: Int,
    val isExpanded: Boolean = false
)

fun SportItem.toItemModel(): SportViewItemModel {
    return SportViewItemModel(
        id = sportId,
        name = sportName,
        events = events.map { event -> event.toItemModel() },
        icon = SportsIcons.values().find { it.sportId == sportId }?.icon
            ?: SportsIcons.FOOTBALL.icon
    )
}
