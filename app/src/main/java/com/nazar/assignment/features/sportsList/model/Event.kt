package com.nazar.assignment.features.sportsList.model

sealed class Event {
    object IOError: Event()
    object HttpError: Event()
    object UnknownError: Event()
}
