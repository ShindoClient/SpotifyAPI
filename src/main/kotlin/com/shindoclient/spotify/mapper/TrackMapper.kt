package com.shindoclient.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Track as SpotifyTrack
import com.wrapper.spotify.model_objects.specification.TrackSimplified as SpotifyTrackSimplified
import com.shindoclient.spotify.data.Track
import com.shindoclient.spotify.data.TrackSimplified

internal fun SpotifyTrackSimplified.toKotlin(): TrackSimplified = TrackSimplified(
    id = id,
    name = name,
    uri = uri,
    href = href,
    durationMs = durationMs,
    trackNumber = trackNumber,
    discNumber = discNumber,
    artists = artists?.map { it.toKotlin() } ?: emptyList(),
    isExplicit = isExplicit,
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
    isPlayable = isPlayable,
)

internal fun SpotifyTrack.toKotlin(): Track = Track(
    id = id,
    name = name,
    uri = uri,
    href = href,
    durationMs = durationMs,
    trackNumber = trackNumber,
    discNumber = discNumber,
    artists = artists?.map { it.toKotlin() } ?: emptyList(),
    album = album?.toKotlin(),
    isExplicit = isExplicit,
    popularity = popularity,
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
    previewUrl = previewUrl,
    isPlayable = isPlayable,
)
