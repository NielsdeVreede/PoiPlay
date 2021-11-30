package com.minor.poiplay

import kotlinx.serialization.Serializable

@Serializable
data class PoiEntity (
    val id: Int,
    val name: String,
    val latitude: String,
    val longitude: String
    ) : java.io.Serializable
