package com.minor.poiplay
import kotlinx.serialization.Serializable


@Serializable
data class AttendantEntity(
    val id: Int,
    val poi_id: Int,
    val name: String,
    val date: String,
    val event_id: Int
): java.io.Serializable
