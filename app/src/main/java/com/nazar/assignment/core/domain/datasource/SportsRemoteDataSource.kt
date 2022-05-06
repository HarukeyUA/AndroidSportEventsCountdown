package com.nazar.assignment.core.domain.datasource

import com.nazar.assignment.core.domain.model.SportItem

interface SportsRemoteDataSource {
    suspend fun getSportsWithEvents(): List<SportItem>
}
