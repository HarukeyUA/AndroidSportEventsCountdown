package com.nazar.assignment

import com.nazar.assignment.core.domain.datasource.SportsRemoteDataSource
import com.nazar.assignment.core.domain.model.SportEvent
import com.nazar.assignment.core.domain.model.SportItem
import java.util.*
import java.util.concurrent.TimeUnit

class SportsRemoteDataSourceMockImpl : SportsRemoteDataSource {

    private val dateOneDayInFuture =
        Date(Date().time + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))

    private val dateOneDayBefore =
        Date(Date().time - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))


    override suspend fun getSportsWithEvents(): List<SportItem> {
        return List(SPORTS_SIZE) { index ->
            val sportId = "sport$index"
            SportItem(
                sportId = sportId,
                sportName = "Sport $index",
                events = getEvents(sportId)
            )
        }
    }

    private fun getEvents(sportId: String): List<SportEvent> {
        return List(EVENTS_SIZE) { index ->
            SportEvent(
                sportId = sportId,
                eventId = "event$index",
                eventName = "Event $index",
                eventStartTime = if (index % 2 == 0) dateOneDayInFuture else dateOneDayBefore
            )
        }
    }

    companion object {
        const val SPORTS_SIZE = 10
        const val FUTURE_EVENTS_SIZE = 10 / 2
        const val EVENTS_SIZE = 10
    }
}