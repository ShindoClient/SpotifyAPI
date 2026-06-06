package me.miki.spotify.mapper

import com.wrapper.spotify.model_objects.miscellaneous.Device as SpotifyDevice
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlaying as SpotifyCurrentlyPlaying
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext
import me.miki.spotify.data.Device
import me.miki.spotify.data.CurrentlyPlaying
import me.miki.spotify.data.PlaybackState

internal fun SpotifyDevice.toKotlin(): Device = Device(
    id = id,
    name = name,
    type = type,
    isActive = is_active,
    isRestricted = is_restricted,
    volumePercent = volume_percent?.let { it },
)

internal fun SpotifyCurrentlyPlaying.toKotlin(): CurrentlyPlaying = CurrentlyPlaying(
    timestamp = timestamp,
    progressMs = progress_ms.toLong(),
    isPlaying = is_playing,
    currentTrack = item?.let {
        if (it is com.wrapper.spotify.model_objects.specification.Track) it.toKotlin()
        else null
    },
)

internal fun CurrentlyPlayingContext.toKotlin(): PlaybackState = PlaybackState(
    device = device?.toKotlin(),
    repeatState = repeat_state,
    shuffleState = shuffle_state,
    isPlaying = is_playing,
    progressMs = progress_ms.toLong(),
    currentTrack = item?.let {
        if (it is com.wrapper.spotify.model_objects.specification.Track) it.toKotlin()
        else null
    },
    volumePercent = device?.volume_percent ?: 100,
)
