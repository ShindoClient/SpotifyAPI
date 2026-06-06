package me.miki.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Paging
import me.miki.spotify.data.PagingInfo

internal fun <T> Paging<T>.toPagingInfo(): PagingInfo = PagingInfo(
    total = total,
    limit = limit,
    offset = offset,
    next = next,
    previous = previous,
)
