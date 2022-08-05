package com.nazar.assignment.features.sportsList

import com.nazar.assignment.MainCoroutineRule
import com.nazar.assignment.SportsRemoteDataSourceMockImpl
import com.nazar.assignment.core.domain.interactor.SportsInteractorImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SportsViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var sportsViewModel: SportsViewModel
    lateinit var sportsRemoteDataSourceMockImpl: SportsRemoteDataSourceMockImpl
    lateinit var sportsInteractorImpl: SportsInteractorImpl

    @Before
    fun init() {
        sportsRemoteDataSourceMockImpl = SportsRemoteDataSourceMockImpl()
        sportsInteractorImpl = SportsInteractorImpl(sportsRemoteDataSourceMockImpl)
        sportsViewModel = SportsViewModel(sportsInteractorImpl)
    }

    @Test
    fun sports_events_list_size() = runTest {
        val sportsList = sportsViewModel.sportsFlow.first()

        assertEquals(SportsRemoteDataSourceMockImpl.SPORTS_SIZE, sportsList.size)
        assertEquals(
            SportsRemoteDataSourceMockImpl.FUTURE_EVENTS_SIZE,
            sportsList.first().events.size
        )
    }

    @Test
    fun sports_closed_by_default() = runTest {
        val sportsList = sportsViewModel.sportsFlow.first()
        assertFalse(sportsList.any { it.isExpanded })
    }

    @Test
    fun events_not_starred_by_default() = runTest {
        val sportsList = sportsViewModel.sportsFlow.first()
        assertFalse(sportsList.any { it.events.any { it.isStarred } })
    }


    @Test
    fun toggle_expanded_state() = runTest {
        var sportsList = sportsViewModel.sportsFlow.first()

        sportsViewModel.toggleExpandedState(sportsList.first())

        sportsList = sportsViewModel.sportsFlow.first()
        assertTrue(sportsList.first().isExpanded)

        sportsViewModel.toggleExpandedState(sportsList.first())

        sportsList = sportsViewModel.sportsFlow.first()
        assertFalse(sportsList.first().isExpanded)
    }

    @Test
    fun toggle_starred_state() = runTest {
        var sportsList = sportsViewModel.sportsFlow.first()

        sportsViewModel.toggleStarredState(sportsList.first().events.first())

        sportsList = sportsViewModel.sportsFlow.first()
        assertTrue(sportsList.first().events.first().isStarred)

        sportsViewModel.toggleStarredState(sportsList.first().events.first())

        sportsList = sportsViewModel.sportsFlow.first()
        assertFalse(sportsList.first().events.first().isStarred)
    }

    @Test
    fun starred_event_is_first() = runTest {
        var sportsList = sportsViewModel.sportsFlow.first()

        val toggleEvent = sportsList.first().events.last()
        sportsViewModel.toggleStarredState(toggleEvent)

        sportsList = sportsViewModel.sportsFlow.first()

        assertTrue(sportsList.first().events.first().isStarred)
        assertEquals(toggleEvent.id, sportsList.first().events.first().id)
    }


}