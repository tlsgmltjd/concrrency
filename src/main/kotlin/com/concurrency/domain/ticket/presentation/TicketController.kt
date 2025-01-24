package com.concurrency.domain.ticket.presentation

import com.concurrency.domain.ticket.application.TicketService
import com.concurrency.domain.ticket.dto.TicketIssueRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/ticket")
class TicketController(
    private val ticketService: TicketService
) {

    @PostMapping("/issue")
    fun issue(
        @RequestParam("userId") userId: Long,
        @RequestParam("ticketId") ticketId: Long
    ): Mono<ResponseEntity<TicketIssueRes>> {
        return ticketService.issue(userId, ticketId)
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

}
