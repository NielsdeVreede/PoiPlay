package com.minor.poiplay
import kotlinx.serialization.Serializable


@Serializable
data class VideoEntity(
    val id: Int,
    val username: String,
    val uri: String,
    val poi_id: Int
) : java.io.Serializable