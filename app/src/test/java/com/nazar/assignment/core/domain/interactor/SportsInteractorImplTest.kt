package com.nazar.assignment.core.domain.interactor

import com.nazar.assignment.SportsRemoteDataSourceMockImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class SportsInteractorImplTest {

    lateinit var mockSportsRemoteDataSource: SportsRemoteDataSourceMockImpl
    lateinit var sportsInteractorImpl: SportsInteractorImpl

    @Before
    fun init() {
        mockSportsRemoteDataSource = SportsRemoteDataSourceMockImpl()
        sportsInteractorImpl = SportsInteractorImpl(mockSportsRemoteDataSource)
    }


    @Test
    fun all_sport_events_in_the_future() = runTest {
        sportsInteractorImpl.fetchSportsList()
        val sportsList = sportsInteractorImpl.sportsFlow.first()

        assertFalse(sportsList.any { it.events.any { it.eventStartTime.before(Date(System.currentTimeMillis())) } })
    }

    @Test
    fun sport_events_sizes() = runTest {
        sportsInteractorImpl.fetchSportsList()
        val sportsList = sportsInteractorImpl.sportsFlow.first()

        assertEquals(SportsRemoteDataSourceMockImpl.SPORTS_SIZE, sportsList.size)
        assertEquals(
            SportsRemoteDataSourceMockImpl.FUTURE_EVENTS_SIZE,
            sportsList.first().events.size
        )
    }
}