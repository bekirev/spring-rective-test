package com.example.reactive

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody

@Component
class GreetingHandler(
    private val requestHandler: RequestHandler
) {
    suspend fun hello(request: ServerRequest): ServerResponse {
        val inputBytes = request.awaitBody<DataBuffer>()
        val returnBytes = requestHandler.handle(inputBytes)
        return ServerResponse.ok()
            .bodyValue(returnBytes)
            .awaitSingle()
    }
}
