package com.example.reactive

import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.channels.Channel
import org.springframework.core.io.buffer.DataBuffer

class RequestChannelRouterImpl(private val channelFactory: () -> Channel<DataBuffer>) : RequestChannelRouter {
    private val requestChannels: MutableMap<RequestId, Channel<DataBuffer>> = ConcurrentHashMap()

    override fun get(requestId: RequestId): Channel<DataBuffer> =
        requestChannels[requestId] ?: error("Unknown requestId: $requestId")

    override suspend fun performRequest(requestId: RequestId, block: suspend () -> Unit): DataBuffer {
        val channel = channelFactory()
        requestChannels[requestId] = channel
        block()
        val result = channel.receive()
        requestChannels.remove(requestId)
        return result
    }
}
