package com.example.reactive

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
class GreetingHandler(
    private val requestHandler: RequestHandler
) {
    fun hello(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
            .body(
                BodyInserters.fromPublisher(
                    mono<DataBuffer> {
                        requestHandler.handle {
                            request.bodyToMono<DataBuffer>().awaitSingle()
                        }
                    },
                    DataBuffer::class.java
                )
            )
    }
}
