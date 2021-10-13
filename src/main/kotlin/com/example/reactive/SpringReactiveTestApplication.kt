package com.example.reactive

import kotlinx.coroutines.channels.Channel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringReactiveTestApplication {
    @Bean
    fun requestChannelRouter(): RequestChannelRouter =
        RequestChannelRouterImpl {
            Channel(capacity = 1)
        }

    @Bean
    fun requestHandler(
        requestChannelRouter: RequestChannelRouter
    ): RequestHandler =
        SameBodyRequestHandler(requestChannelRouter)
}

fun main(args: Array<String>) {
    runApplication<SpringReactiveTestApplication>(*args)
}
