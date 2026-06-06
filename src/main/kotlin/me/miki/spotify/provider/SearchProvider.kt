package me.miki.spotify.provider

import me.miki.spotify.Spotify
import me.miki.spotify.data.SearchResults
import me.miki.spotify.data.Track
import me.miki.spotify.data.AlbumSimplified
import me.miki.spotify.data.Artist
import me.miki.spotify.data.PlaylistSimplified
import me.miki.spotify.mapper.toKotlin
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
            spotify.api.searchTracks(query).limit(limit).offset(offset).build()
                .execute().items?.map { it.toKotlin() } ?: emptyList()
        } else emptyList()

        val albums = if (SearchType.ALBUM in types) {
            spotify.api.searchAlbums(query).limit(limit).offset(offset).build()
                .execute().items?.map { it.toKotlin() } ?: emptyList()
        } else emptyList()

        val artists = if (SearchType.ARTIST in types) {
            spotify.api.searchArtists(query).limit(limit).offset(offset).build()
                .execute().items?.map { it.toKotlin() } ?: emptyList()
        } else emptyList()

        val playlists = if (SearchType.PLAYLIST in types) {
            spotify.api.searchPlaylists(query).limit(limit).offset(offset).build()
                .execute().items?.map { it.toKotlin() } ?: emptyList()
        } else emptyList()

        SearchResults(tracks = tracks, albums = albums, artists = artists, playlists = playlists)
    }

    fun searchTracks(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<Track>> = spotify.withRateLimit {
        spotify.api.searchTracks(query).limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() } ?: emptyList()
    }

    fun searchAlbums(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<AlbumSimplified>> = spotify.withRateLimit {
        spotify.api.searchAlbums(query).limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() } ?: emptyList()
    }

    fun searchArtists(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<Artist>> = spotify.withRateLimit {
        spotify.api.searchArtists(query).limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() } ?: emptyList()
    }

    fun searchPlaylists(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<PlaylistSimplified>> = spotify.withRateLimit {
        spotify.api.searchPlaylists(query).limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() } ?: emptyList()
    }
}

enum class SearchType {
    TRACK, ALBUM, ARTIST, PLAYLIST,
}
