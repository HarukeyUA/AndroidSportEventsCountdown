package com.nazar.assignment.core.data.response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class EventItemResponse(
    @SerializedName("d")
    val eventName: String?,
    @SerializedName("i")
    val eventId: String?,
    @SerializedName("si")
    val sportId: String?,
    @SerializedName("tt")
    val eventStartTime: Long?
)
