package com.nazar.assignment.network

import com.nazar.assignment.core.data.response.SportItemResponse
import retrofit2.http.GET

interface ApiSports {
    @GET("/demo")
    suspend fun getSportsWithEvents(): List<SportItemResponse>
}
