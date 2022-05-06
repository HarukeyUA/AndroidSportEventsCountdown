package com.nazar.assignment.features.sportsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazar.assignment.core.domain.interactor.SportsInteractor
import com.nazar.assignment.features.sportsList.model.Event
import com.nazar.assignment.features.sportsList.model.EventViewItemModel
import com.nazar.assignment.features.sportsList.model.SportViewItemModel
import com.nazar.assignment.features.sportsList.model.toItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SportsViewModel @Inject constructor(private val sportsInteractor: SportsInteractor) :
    ViewModel() {

    private val expandedSports = MutableStateFlow(setOf<String>())
    private val starredEvents = MutableStateFlow(setOf<String>())

    val sportsFlow = sportsInteractor.sportsFlow.map { sports ->
        sports.map { it.toItemModel() }
    }.combine(expandedSports) { sports: List<SportViewItemModel>, expanded: Set<String> ->
        sports.map { sport ->
            setExpandedSports(sport, expanded)
        }
    }.combine(starredEvents) { sports: List<SportViewItemModel>, starredEvents: Set<String> ->
        sports.map { sport ->
            setStarredEvents(sport, starredEvents)
        }
    }.flowOn(Dispatchers.IO)

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val defaultCoroutineExceptionHandler =
        CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
            when (throwable) {
                is IOException -> {
                    eventChannel.trySend(Event.IOError)
                }
                is HttpException -> {
                    eventChannel.trySend(Event.HttpError)
                }
                else -> {
                    eventChannel.trySend(Event.UnknownError)
                }
            }

        }

    init {
        fetchSports()
    }

    fun fetchSports() {
        viewModelScope.launch(defaultCoroutineExceptionHandler) {
            sportsInteractor.fetchSportsList()
        }
    }

    fun toggleExpandedState(item: SportViewItemModel) {
        val currentSet = expandedSports.value.toMutableSet()
        if (item.isExpanded)
            currentSet.remove(item.id)
        else
            currentSet.add(item.id)
        expandedSports.value = currentSet
    }

    fun toggleStarredState(item: EventViewItemModel) {
        val currentSet = starredEvents.value.toMutableSet()
        if (item.isStarred)
            currentSet.remove(item.id)
        else
            currentSet.add(item.id)
        starredEvents.value = currentSet
    }

    private fun setExpandedSports(
        sport: SportViewItemModel,
        expanded: Set<String>
    ) = sport.copy(isExpanded = expanded.any { id -> id == sport.id })

    private fun setStarredEvents(
        sport: SportViewItemModel,
        starredEvents: Set<String>
    ) = sport.copy(
        events = sport.events.map { event ->
            event.copy(isStarred = starredEvents.any { id -> id == event.id })
        }.sortedByDescending { it.isStarred }
    )
}
