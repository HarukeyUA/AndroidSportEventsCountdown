package com.nazar.assignment.core.data.datasource

import com.nazar.assignment.core.domain.datasource.SportsRemoteDataSource
import com.nazar.assignment.core.domain.model.SportItem
import com.nazar.assignment.core.domain.model.toDomain
import com.nazar.assignment.network.ApiSports
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SportRemoteDataSourceImpl @Inject constructor(
    private val sports: ApiSports
) : SportsRemoteDataSource {
    override suspend fun getSportsWithEvents(): List<SportItem> {
        return sports.getSportsWithEvents()
            .mapNotNull { sportItemResponse -> sportItemResponse.toDomain() }
    }

}
