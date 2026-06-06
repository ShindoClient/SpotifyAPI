package me.miki.spotify.data

data class CurrentlyPlaying(
    val timestamp: Long,
    val progressMs: Long,
    val isPlaying: Boolean,
    val currentTrack: Track?,
)
