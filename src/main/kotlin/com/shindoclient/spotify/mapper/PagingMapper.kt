package com.shindoclient.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Paging
import com.shindoclient.spotify.data.PagingInfo

internal fun <T> Paging<T>.toPagingInfo(): PagingInfo = PagingInfo(
    total = total,
    limit = limit,
    offset = offset,
    next = next,
    previous = previous,
)
