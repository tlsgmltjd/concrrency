package com.concurrency.domain.user.controller

import com.concurrency.domain.user.dto.UserResponse
import com.concurrency.domain.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{user_id}")
    fun queryById(@PathVariable("user_id") userId: Long): Mono<UserResponse> {
        return userService.queryById(userId)
    }

}