package com.minor.poiplay
import kotlinx.serialization.Serializable

@Serializable
data class EventEntity(
    val id: String,
    val name: String,
    val poi_id: Int,
    val time: String
) : java.io.Serializable