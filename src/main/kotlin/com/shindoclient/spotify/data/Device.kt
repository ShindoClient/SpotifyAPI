package com.shindoclient.spotify.data

data class Device(
    val id: String?,
    val name: String,
    val type: String,
    val isActive: Boolean,
    val isRestricted: Boolean,
    val volumePercent: Int?,
)
