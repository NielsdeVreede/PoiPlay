package com.minor.poiplay
import com.minor.poiplay.Components.EventAttendant
import kotlinx.serialization.Serializable

@Serializable
data class EventEntity(
    val id: Int,
    val name: String,
    val poi_id: Int,
    val time: String,
    val attendees: List<AttendantEntity> = listOf()
) : java.io.Serializable