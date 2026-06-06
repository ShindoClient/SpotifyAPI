package com.shindoclient.spotify.data

import java.time.Instant

data class PlaylistTrack(
    val addedAt: Instant?,
    val addedBy: User?,
    val track: Track?,
)
