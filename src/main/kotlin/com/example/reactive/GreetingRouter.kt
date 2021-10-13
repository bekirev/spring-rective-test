package com.example.reactive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.all
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration(proxyBeanMethods = false)
class GreetingRouter {
    @Bean
    fun route(greetingHandler: GreetingHandler): RouterFunction<ServerResponse> =
        route(all()) { request: ServerRequest ->
            greetingHandler.hello(
                request
            )
        }
}
