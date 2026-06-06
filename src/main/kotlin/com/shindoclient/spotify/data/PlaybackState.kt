package com.shindoclient.spotify.data

data class PlaybackState(
    val device: Device?,
    val repeatState: String,
    val shuffleState: Boolean,
    val isPlaying: Boolean,
    val progressMs: Long,
    val currentTrack: Track?,
    val volumePercent: Int,
)
