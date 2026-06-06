package com.shindoclient.spotify.provider

import com.google.gson.JsonArray
import com.shindoclient.spotify.Spotify
import com.shindoclient.spotify.data.Device
import com.shindoclient.spotify.data.CurrentlyPlaying
import com.shindoclient.spotify.data.PlaybackState
import com.shindoclient.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class PlayerProvider internal constructor(
    private val spotify: Spotify,
) {
    fun getAvailableDevices(): CompletableFuture<List<Device>> = spotify.withRateLimit {
        spotify.api.getUsersAvailableDevices().build().execute()
            .map { it.toKotlin() }.toList()
    }

    fun getPlaybackState(): CompletableFuture<PlaybackState?> = spotify.withRateLimit {
        spotify.api.getInformationAboutUsersCurrentPlayback().build().execute()
            ?.toKotlin()
    }

    fun getCurrentlyPlaying(): CompletableFuture<CurrentlyPlaying?> = spotify.withRateLimit {
        spotify.api.getUsersCurrentlyPlayingTrack().build().execute()
            ?.toKotlin()
    }

    fun startResumePlayback(
        deviceId: String? = null,
        contextUri: String? = null,
        uris: List<String>? = null,
        positionMs: Int? = null,
    ): CompletableFuture<Void?> = spotify.withRateLimit {
        val builder = spotify.api.startResumeUsersPlayback()
        if (deviceId != null) builder.device_id(deviceId)
        if (contextUri != null) builder.context_uri(contextUri)
        if (uris != null) builder.uris(JsonArray().apply { uris.forEach { add(it) } })
        if (positionMs != null) builder.position_ms(positionMs)
        builder.build().execute()
        null
    }

    fun pausePlayback(deviceId: String? = null): CompletableFuture<Void?> = spotify.withRateLimit {
        val builder = spotify.api.pauseUsersPlayback()
        if (deviceId != null) builder.device_id(deviceId)
        builder.build().execute()
        null
    }

    fun nextTrack(deviceId: String? = null): CompletableFuture<Void?> = spotify.withRateLimit {
        val builder = spotify.api.skipUsersPlaybackToNextTrack()
        if (deviceId != null) builder.device_id(deviceId)
        builder.build().execute()
        null
    }

    fun previousTrack(deviceId: String? = null): CompletableFuture<Void?> = spotify.withRateLimit {
        val builder = spotify.api.skipUsersPlaybackToPreviousTrack()
        if (deviceId != null) builder.device_id(deviceId)
        builder.build().execute()
        null
    }

    fun seekTo(positionMs: Int): CompletableFuture<Void?> = spotify.withRateLimit {
        spotify.api.seekToPositionInCurrentlyPlayingTrack(positionMs).build().execute()
        null
    }

    fun setVolume(volumePercent: Int): CompletableFuture<Void?> = spotify.withRateLimit {
        spotify.api.setVolumeForUsersPlayback(volumePercent).build().execute()
        null
    }

    fun setRepeatMode(mode: RepeatMode): CompletableFuture<Void?> = spotify.withRateLimit {
        spotify.api.setRepeatModeOnUsersPlayback(mode.state).build().execute()
        null
    }

    fun setShuffle(shuffle: Boolean): CompletableFuture<Void?> = spotify.withRateLimit {
        spotify.api.toggleShuffleForUsersPlayback(shuffle).build().execute()
        null
    }

    fun transferPlayback(deviceId: String, play: Boolean = true): CompletableFuture<Void?> = spotify.withRateLimit {
        spotify.api.transferUsersPlayback(JsonArray().apply { add(deviceId) }).play(play).build().execute()
        null
    }

    fun addToQueue(trackUri: String, deviceId: String? = null): CompletableFuture<Void?> = spotify.withRateLimit {
        val builder = spotify.api.addItemToUsersPlaybackQueue(trackUri)
        if (deviceId != null) builder.device_id(deviceId)
        builder.build().execute()
        null
    }
}

enum class RepeatMode(val state: String) {
    OFF("off"),
    CONTEXT("context"),
    TRACK("track"),
}
