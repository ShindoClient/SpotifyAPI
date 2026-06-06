package com.shindoclient.spotify.provider

import com.shindoclient.spotify.Spotify
import com.shindoclient.spotify.data.SearchResults
import com.shindoclient.spotify.data.Track
import com.shindoclient.spotify.data.AlbumSimplified
import com.shindoclient.spotify.data.Artist
import com.shindoclient.spotify.data.PlaylistSimplified
import com.shindoclient.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class SearchProvider internal constructor(
    private val spotify: Spotify,
) {
    fun search(
        query: String,
        types: Set<SearchType> = SearchType.entries.toSet(),
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<SearchResults> = spotify.withRateLimit {
        val tracks = if (SearchType.TRACK in types) {
            safeItems {
                spotify.api.searchTracks(query).limit(limit).offset(offset).build()
                    .execute()?.items
            }.map { it.toKotlin() }
        } else emptyList()

        val albums = if (SearchType.ALBUM in types) {
            safeItems {
                spotify.api.searchAlbums(query).limit(limit).offset(offset).build()
                    .execute()?.items
            }.map { it.toKotlin() }
        } else emptyList()

        val artists = if (SearchType.ARTIST in types) {
            safeItems {
                spotify.api.searchArtists(query).limit(limit).offset(offset).build()
                    .execute()?.items
            }.map { it.toKotlin() }
        } else emptyList()

        val playlists = if (SearchType.PLAYLIST in types) {
            safeItems {
                spotify.api.searchPlaylists(query).limit(limit).offset(offset).build()
                    .execute()?.items
            }.map { it.toKotlin() }
        } else emptyList()

        SearchResults(tracks = tracks, albums = albums, artists = artists, playlists = playlists)
    }

    fun searchTracks(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<Track>> = spotify.withRateLimit {
        safeItems {
            spotify.api.searchTracks(query).limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }

    fun searchAlbums(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<AlbumSimplified>> = spotify.withRateLimit {
        safeItems {
            spotify.api.searchAlbums(query).limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }

    fun searchArtists(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<Artist>> = spotify.withRateLimit {
        safeItems {
            spotify.api.searchArtists(query).limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }

    fun searchPlaylists(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<PlaylistSimplified>> = spotify.withRateLimit {
        safeItems {
            spotify.api.searchPlaylists(query).limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }
}

enum class SearchType {
    TRACK, ALBUM, ARTIST, PLAYLIST,
}
