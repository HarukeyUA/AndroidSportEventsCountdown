package com.nazar.assignment.core.domain.interactor

import com.nazar.assignment.core.domain.datasource.SportsRemoteDataSource
import com.nazar.assignment.core.domain.model.SportItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SportsInteractor {
    val sportsFlow: Flow<List<SportItem>>
    suspend fun fetchSportsList()
}

class SportsInteractorImpl @Inject constructor(private val remoteDataSource: SportsRemoteDataSource) :
    SportsInteractor {
    private val _sportsFlow = MutableStateFlow<List<SportItem>>(listOf())
    override val sportsFlow: Flow<List<SportItem>>
        get() = _sportsFlow.map { sports ->
            sports.map { sportItem ->
                processSportEvents(sportItem)
            }
        }

    private fun processSportEvents(sportItem: SportItem) =
        sportItem.copy(
            events = sportItem.events.filter { event ->
                event.eventStartTime.time > System.currentTimeMillis()
            }.sortedBy { event ->
                event.eventStartTime
            })

    override suspend fun fetchSportsList() {
        _sportsFlow.value = remoteDataSource.getSportsWithEvents()
    }
}
