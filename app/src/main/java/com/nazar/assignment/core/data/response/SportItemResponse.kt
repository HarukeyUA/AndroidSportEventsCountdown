package com.nazar.assignment.core.data.response


import com.google.gson.annotations.SerializedName

data class SportItemResponse(
    @SerializedName("i")
    val sportId: String?,
    @SerializedName("d")
    val sportName: String?,
    @SerializedName("e")
    val events: List<EventItemResponse>?
)
