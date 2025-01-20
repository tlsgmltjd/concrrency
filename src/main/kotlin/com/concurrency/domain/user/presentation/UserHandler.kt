package com.concurrency.domain.user.presentation

import com.concurrency.domain.user.application.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class UserHandler(
    private val userService: UserService
) {

    fun queryByUserId(serverRequest: ServerRequest): Mono<ServerResponse> {
        val userId = serverRequest.pathVariable("id")

        val response = userService.queryById(userId.toLong())

        return ServerResponse.ok().body(response)
    }

}