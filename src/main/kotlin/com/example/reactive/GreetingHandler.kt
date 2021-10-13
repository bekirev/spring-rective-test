package com.example.reactive

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
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
    companion object {
        private val LOG = LoggerFactory.getLogger(GreetingHandler::class.java)
    }

    fun hello(request: ServerRequest): Mono<ServerResponse> {
//        request
//            .bodyToMono(String::class.java)
//            .subscribe { body ->
//                println(
//                    "Handle request [request = '${request}', headers = '${request.headers()}', body = '$body'"
//                )
//                LOG.info(
//                    "Handle request [request = '{}', headers = '{}', body = '{}'",
//                    request.toString(),
//                    request.headers(),
//                    body
//                )
//            }
//            .body { inputMessage, _ ->
//                inputMessage.body
//                    .reduce(StringBuilder()) { acc, value ->
//                        val stringPart = value.asOutputStream().use {
//                            it.toString()
//                        }
//                        acc.append(stringPart)
//                    }
//                    .map(StringBuilder::toString)
//                    .subscribe { body ->
//                        LOG.info(
//                            "Handle request [request = '{}', headers = '{}', body = '{}'",
//                            request.toString(),
//                            request.headers(),
//                            body
//                        )
//                    }
//            }
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
