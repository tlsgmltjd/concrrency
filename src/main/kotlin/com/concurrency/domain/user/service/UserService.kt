package com.concurrency.domain.user.service

import com.concurrency.domain.user.domain.UserRepository
import com.concurrency.domain.user.dto.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun queryById(request: Long): Mono<UserResponse> =
        userRepository.findById(request)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
            .map { UserResponse(it.id, it.name) }

}