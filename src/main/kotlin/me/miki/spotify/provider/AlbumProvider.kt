package me.miki.spotify.provider

import me.miki.spotify.Spotify
import me.miki.spotify.data.Album
import me.miki.spotify.data.AlbumSimplified
import me.miki.spotify.data.Track
import me.miki.spotify.data.TrackSimplified
import me.miki.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class AlbumProvider internal constructor(
    private val spotify: Spotify,
) {
    fun getAlbum(albumId: String): CompletableFuture<Album> = spotify.withRateLimit {
        spotify.api.getAlbum(albumId).build().execute().toKotlin()
    }

    fun getSeveralAlbums(albumIds: List<String>): CompletableFuture<List<Album>> = spotify.withRateLimit {
        spotify.api.getSeveralAlbums(*albumIds.toTypedArray()).build()
            .execute().map { it.toKotlin() }.toList()
    }

    fun getAlbumTracks(
        albumId: String,
        limit: Int = 50,
        offset: Int = 0,
    ): CompletableFuture<List<TrackSimplified>> = spotify.withRateLimit {
        spotify.api.getAlbumsTracks(albumId).limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() } ?: emptyList()
    }

    fun getSeveralTracks(trackIds: List<String>): CompletableFuture<List<Track>> = spotify.withRateLimit {
        spotify.api.getSeveralTracks(*trackIds.toTypedArray()).build()
            .execute().map { it.toKotlin() }.toList()
    }

    fun getNewReleases(
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<AlbumSimplified>> = spotify.withRateLimit {
        spotify.api.getListOfNewReleases().limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() }?.toList() ?: emptyList()
    }
}
