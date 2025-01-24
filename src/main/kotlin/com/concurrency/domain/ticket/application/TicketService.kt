package com.concurrency.domain.ticket.application

import com.concurrency.domain.ticket.dto.TicketIssueRes
import com.concurrency.domain.ticket.persistence.TicketRepository
import com.concurrency.domain.ticket.persistence.TicketUser
import com.concurrency.domain.ticket.persistence.TicketUserRepository
import com.concurrency.domain.user.persistence.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class TicketService(
    private val userRepository: UserRepository,
    private val ticketRepository: TicketRepository,
    private val ticketUserRepository: TicketUserRepository
) {

    @Transactional
    fun issueNolock(userId: Long, ticketId: Long): Mono<TicketIssueRes> {
        return userRepository.findById(userId)
            .flatMap { user ->
                ticketRepository.findById(ticketId)
                    .flatMap { ticket ->
                        ticket.addCount()
                        ticketRepository.save(ticket)
                            .flatMap { savedTicket ->
                                val ticketUser = TicketUser.of(user.id)
                                ticketUserRepository.save(ticketUser)
                                    .map { savedTicketUser ->
                                        TicketIssueRes(
                                            ticketId = savedTicket.id,
                                            ticketName = savedTicket.name,
                                            count = savedTicket.count,
                                            limitCount = savedTicket.limitCount,
                                            date = savedTicketUser.createTime
                                        )
                                    }
                            }
                    }
            }
    }
}
