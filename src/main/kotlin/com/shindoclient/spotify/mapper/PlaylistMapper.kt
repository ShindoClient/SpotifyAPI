package com.shindoclient.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Playlist as SpotifyPlaylist
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified as SpotifyPlaylistSimplified
import com.wrapper.spotify.model_objects.specification.PlaylistTrack as SpotifyPlaylistTrack
import com.shindoclient.spotify.data.Playlist
import com.shindoclient.spotify.data.PlaylistSimplified
import com.shindoclient.spotify.data.PlaylistTrack
import com.shindoclient.spotify.data.Followers

internal fun SpotifyPlaylistSimplified.toKotlin(): PlaylistSimplified = PlaylistSimplified(
    id = id,
    name = name,
    uri = uri,
    href = href,
    images = images?.toKotlinList() ?: emptyList(),
    owner = owner?.toKotlin(),
    isPublicAccess = isPublicAccess,
    collaborative = isCollaborative,
    tracksTotal = tracks?.total ?: 0,
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
    snapshotId = snapshotId,
)

internal fun SpotifyPlaylistTrack.toKotlin(): PlaylistTrack = PlaylistTrack(
    addedAt = addedAt?.toInstant(),
    addedBy = addedBy?.toKotlin(),
    track = track?.let {
        if (it is com.wrapper.spotify.model_objects.specification.Track) it.toKotlin()
        else null
    },
)

internal fun SpotifyPlaylist.toKotlin(): Playlist = Playlist(
    id = id,
    name = name,
    uri = uri,
    href = href,
    description = description,
    images = images?.toKotlinList() ?: emptyList(),
    owner = owner?.toKotlin(),
    isPublicAccess = isPublicAccess,
    collaborative = isCollaborative,
    tracks = tracks?.items?.map { it.toKotlin() }?.toList() ?: emptyList(),
    tracksTotal = tracks?.total ?: 0,
    followers = followers?.let { Followers(href = it.href, total = it.total) },
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
    snapshotId = snapshotId,
)
