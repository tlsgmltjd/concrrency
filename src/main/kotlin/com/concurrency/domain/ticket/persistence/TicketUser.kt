package com.concurrency.domain.ticket.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "tbl_ticket_user")
data class TicketUser(
    @Id
    val id: Long = 0,
    val userId: Long,
    val createTime: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun of(userId: Long): TicketUser {
            return TicketUser(userId = userId)
        }
    }
}
