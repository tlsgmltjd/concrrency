package com.concurrency.domain.user.presentation

import com.concurrency.domain.user.application.UserService
import com.concurrency.domain.user.dto.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun queryUSerById(@PathVariable("user_id") userId: Long): Mono<ResponseEntity<UserResponse>> {
        val response = userService.queryById(userId)
        return response.map { ResponseEntity(it, HttpStatus.OK) }
    }

}
