package com.shindoclient.spotify.provider

import com.google.gson.JsonArray
import com.shindoclient.spotify.Spotify
import com.shindoclient.spotify.data.Playlist
import com.shindoclient.spotify.data.PlaylistSimplified
import com.shindoclient.spotify.data.PlaylistTrack
import com.shindoclient.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class PlaylistProvider internal constructor(
    private val spotify: Spotify,
) {
    fun getPlaylist(playlistId: String): CompletableFuture<Playlist> = spotify.withRateLimit {
        safeCall { spotify.api.getPlaylist(playlistId).build().execute() }?.toKotlin()
            ?: throw IllegalStateException("Failed to get playlist: $playlistId")
    }

    fun getPlaylistTracks(
        playlistId: String,
        limit: Int = 100,
        offset: Int = 0,
    ): CompletableFuture<List<PlaylistTrack>> = spotify.withRateLimit {
        safeItems {
            spotify.api.getPlaylistsItems(playlistId)
                .limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }

    fun getAllPlaylistTracks(playlistId: String): CompletableFuture<List<PlaylistTrack>> =
        CompletableFuture.supplyAsync {
            val allTracks = mutableListOf<PlaylistTrack>()
            var offset = 0
            val limit = 100
            var pageCount = 0
            val maxPages = 20

            while (pageCount < maxPages) {
                val page = safeCall {
                    spotify.api.getPlaylistsItems(playlistId)
                        .limit(limit).offset(offset).build().execute()
                } ?: break
                val items = page.items?.map { it.toKotlin() } ?: break
                if (items.isEmpty()) break
                allTracks.addAll(items)
                offset += items.size
                pageCount++
                spotify.rateLimiter.acquire()
            }

            allTracks
        }

    fun createPlaylist(
        userId: String,
        name: String,
        description: String? = null,
        isPublic: Boolean = true,
        isCollaborative: Boolean = false,
    ): CompletableFuture<Playlist> = spotify.withRateLimit {
        val builder = spotify.api.createPlaylist(userId, name)
        builder.public_(isPublic)
        builder.collaborative(isCollaborative)
        if (description != null) builder.description(description)
        safeCall { builder.build().execute() }?.toKotlin()
            ?: throw IllegalStateException("Failed to create playlist")
    }

    fun addItemsToPlaylist(
        playlistId: String,
        uris: List<String>,
        position: Int? = null,
    ): CompletableFuture<String?> = spotify.withRateLimit {
        val builder = spotify.api.addItemsToPlaylist(playlistId, uris.toTypedArray())
        if (position != null) builder.position(position)
        safeCall { builder.build().execute() }?.snapshotId
    }

    fun removeItemsFromPlaylist(
        playlistId: String,
        uris: List<String>,
    ): CompletableFuture<String?> = spotify.withRateLimit {
        val jsonArray = JsonArray().apply { uris.forEach { add(it) } }
        safeCall {
            spotify.api.removeItemsFromPlaylist(playlistId, jsonArray)
                .build().execute()
        }?.snapshotId
    }

    fun changePlaylistDetails(
        playlistId: String,
        name: String? = null,
        description: String? = null,
        isPublic: Boolean? = null,
        isCollaborative: Boolean? = null,
    ): CompletableFuture<Void?> = spotify.withRateLimit {
        val builder = spotify.api.changePlaylistsDetails(playlistId)
        if (name != null) builder.name(name)
        if (description != null) builder.description(description)
        if (isPublic != null) builder.public_(isPublic)
        if (isCollaborative != null) builder.collaborative(isCollaborative)
        safeCall { builder.build().execute() }
        null
    }
}
