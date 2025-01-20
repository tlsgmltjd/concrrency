package com.concurrency.domain.user.presentation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest


@Configuration
class UserRouter(
    private val userHandler: UserHandler,
) {

    @Bean
    fun userBaseRouter() = nest(path("/user"),
            router {
                listOf(
                    GET("/{id}", userHandler::queryByUserId)
                )
            }
        )

}
