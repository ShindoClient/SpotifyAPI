package me.miki.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Track as SpotifyTrack
import com.wrapper.spotify.model_objects.specification.TrackSimplified as SpotifyTrackSimplified
import me.miki.spotify.data.Track
import me.miki.spotify.data.TrackSimplified

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
