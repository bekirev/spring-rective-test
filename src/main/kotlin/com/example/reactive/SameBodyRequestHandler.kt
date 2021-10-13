package com.example.reactive

import java.util.UUID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.core.io.buffer.DataBuffer

class SameBodyRequestHandler(
    private val requestChannelRouter: RequestChannelRouter
) : RequestHandler {
    override suspend fun handle(dataBufferSupplier: suspend () -> DataBuffer): DataBuffer {
        val requestId = UUID.randomUUID()
        return requestChannelRouter.performRequest(requestId = requestId) {
            GlobalScope.launch {
                requestChannelRouter[requestId]!!.send(dataBufferSupplier())
            }
        }
    }
}
