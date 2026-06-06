@file:Suppress("UNCHECKED_CAST")

package com.shindoclient.spotify.provider

/**
 * Safely executes an API call that returns an array (e.g., search items, playlist tracks).
 * Handles null execute(), null items array, null elements inside the array,
 * and any exceptions from the API call — all return [emptyList].
 */
internal fun <T> safeItems(call: () -> Array<out T>?): List<T> =
    try {
        (call() as? Array<out T?>)?.filterNotNull().orEmpty()
    } catch (_: Exception) {
        emptyList()
    }

/**
 * Safely executes an API call that returns a nullable single object.
 * Handles null return and any exceptions — returns null on failure.
 */
internal fun <T> safeCall(call: () -> T?): T? =
    try {
        call()
    } catch (_: Exception) {
        null
    }
