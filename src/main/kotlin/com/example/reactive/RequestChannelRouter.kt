package com.example.reactive

import java.util.UUID
import kotlinx.coroutines.channels.Channel
import org.springframework.core.io.buffer.DataBuffer

typealias RequestId = UUID

interface RequestChannelRouter {
    operator fun get(requestId: RequestId): Channel<DataBuffer>
    suspend fun performRequest(requestId: RequestId, block: suspend () -> Unit): DataBuffer
}
