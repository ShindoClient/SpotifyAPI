package com.shindoclient.spotify.provider

import com.shindoclient.spotify.Spotify
import com.shindoclient.spotify.data.Album
import com.shindoclient.spotify.data.AlbumSimplified
import com.shindoclient.spotify.data.Track
import com.shindoclient.spotify.data.TrackSimplified
import com.shindoclient.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class AlbumProvider internal constructor(
    private val spotify: Spotify,
) {
    fun getAlbum(albumId: String): CompletableFuture<Album> = spotify.withRateLimit {
        safeCall { spotify.api.getAlbum(albumId).build().execute() }?.toKotlin()
            ?: throw IllegalStateException("Failed to get album: $albumId")
    }

    fun getSeveralAlbums(albumIds: List<String>): CompletableFuture<List<Album>> = spotify.withRateLimit {
        safeCall {
            spotify.api.getSeveralAlbums(*albumIds.toTypedArray()).build().execute()
        }?.map { it.toKotlin() }?.toList().orEmpty()
    }

    fun getAlbumTracks(
        albumId: String,
        limit: Int = 50,
        offset: Int = 0,
    ): CompletableFuture<List<TrackSimplified>> = spotify.withRateLimit {
        safeItems {
            spotify.api.getAlbumsTracks(albumId).limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }

    fun getSeveralTracks(trackIds: List<String>): CompletableFuture<List<Track>> = spotify.withRateLimit {
        safeCall {
            spotify.api.getSeveralTracks(*trackIds.toTypedArray()).build().execute()
        }?.map { it.toKotlin() }?.toList().orEmpty()
    }

    fun getNewReleases(
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<AlbumSimplified>> = spotify.withRateLimit {
        safeItems {
            spotify.api.getListOfNewReleases().limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }
}
